define(['controller/selectionController', 'model/cacheModel', 'model/estadisticasMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/estadisticasComponent',
 'component/dia_InscripcionComponent', 'component/empresaComponent'],
 function(SelectionController, CacheModel, EstadisticasMasterModel, CRUDComponent, TabController, EstadisticasComponent,
 dia_InscripcionComponent, empresaComponent) {
    App.Component._EstadisticasMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('estadisticasMaster');
            App.Model.EstadisticasMasterModel.prototype.urlRoot = this.configuration.context;
            this.componentId = App.Utils.randomInteger();
            
            this.masterComponent = new EstadisticasComponent();
            this.masterComponent.initialize();
            
            this.childComponents = [];
			
			this.initializeChildComponents();
            
            Backbone.on(this.masterComponent.componentId + '-post-estadisticas-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(this.masterComponent.componentId + '-post-estadisticas-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(this.masterComponent.componentId + '-pre-estadisticas-list', function() {
                self.hideChilds();
            });
            Backbone.on('estadisticas-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'estadisticas-master-save', view: self, message: error});
            });
            Backbone.on(this.masterComponent.componentId + '-instead-estadisticas-save', function(params) {
                self.model.set('estadisticasEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }

				App.Utils.fillCacheList(
					'dia_Inscripcion',
					self.model,
					self.dia_InscripcionComponent.getDeletedRecords(),
					self.dia_InscripcionComponent.getUpdatedRecords(),
					self.dia_InscripcionComponent.getCreatedRecords()
				);

				App.Utils.fillCacheList(
					'empresa',
					self.model,
					self.empresaComponent.getDeletedRecords(),
					self.empresaComponent.getUpdatedRecords(),
					self.empresaComponent.getCreatedRecords()
				);

                self.model.save({}, {
                    success: function() {
                        Backbone.trigger(self.masterComponent.componentId + '-' + 'post-estadisticas-save', {view: self, model : self.model});
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'estadisticas-master-save', view: self, error: error});
                    }
                });
			    if (this.postInit) {
					this.postInit();
				}
            });
        },
        render: function(domElementId){
			if (domElementId) {
				var rootElementId = $("#"+domElementId);
				this.masterElement = this.componentId + "-master";
				this.tabsElement = this.componentId + "-tabs";

				rootElementId.append("<div id='" + this.masterElement + "'></div>");
				rootElementId.append("<div id='" + this.tabsElement + "'></div>");
			}
			this.masterComponent.render(this.masterElement);
		},
		initializeChildComponents: function () {
			this.tabModel = new App.Model.TabModel({tabs: [
                {label: "Dia_ Inscripcion", name: "dia_Inscripcion", enable: true},
                {label: "Empresa", name: "empresa", enable: true}
			]});
			this.tabs = new TabController({model: this.tabModel});

			this.dia_InscripcionComponent = new dia_InscripcionComponent();
            this.dia_InscripcionComponent.initialize({cache: {data: [], mode: "memory"},pagination: false});
			this.childComponents.push(this.dia_InscripcionComponent);

			this.empresaComponent = new empresaComponent();
            this.empresaComponent.initialize({cache: {data: [], mode: "memory"},pagination: false});
			this.childComponents.push(this.empresaComponent);

            var self = this;
            
            this.configToolbar(this.dia_InscripcionComponent,true);
            Backbone.on(self.dia_InscripcionComponent.componentId + '-post-dia_Inscripcion-create', function(params) {
                params.view.currentModel.setCacheList(params.view.currentList);
            });
            
            this.configToolbar(this.empresaComponent,false);
            this.empresaComponent.disableEdit();

            Backbone.on(this.empresaComponent.componentId + '-toolbar-add', function() {
                var selection = new SelectionController({"componentId":"empresaComponent"});
                App.Utils.getComponentList('empresaComponent', function(componentName, model) {
                    if (model.models.length == 0) {
                        alert('There is no Empresas to select.');
                    } else {
                        selection.showSelectionList({list: model, name: 'name', title: 'Empresa List'});
                    }
                    ;
                });
            });

            Backbone.on('empresaComponent-post-selection', function(models) {
            	self.empresaComponent.addRecords(models);
            	self.empresaComponent.render();
            });

		},
        renderChilds: function(params) {
            var self = this;
            
            var options = {
                success: function() {
                	self.tabs.render(self.tabsElement);

					self.dia_InscripcionComponent.clearCache();
					self.dia_InscripcionComponent.setRecords(self.model.get('listdia_Inscripcion'));
					self.dia_InscripcionComponent.render(self.tabs.getTabHtmlId('dia_Inscripcion'));

					self.empresaComponent.clearCache();
					self.empresaComponent.setRecords(self.model.get('listempresa'));
					self.empresaComponent.render(self.tabs.getTabHtmlId('empresa'));

                    $('#'+self.tabsElement).show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'estadisticas-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.EstadisticasMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.EstadisticasMasterModel();
                options.success();
            }


        },
        showMaster: function (flag) {
			if (typeof (flag) === "boolean") {
				if (flag) {
					$("#"+this.masterElement).show();
				} else {
					$("#"+this.masterElement).hide();
				}
			}
		},
        hideChilds: function() {
            $("#"+this.tabsElement).hide();
        },
		configToolbar: function(component, composite) {
		    component.removeGlobalAction('refresh');
			component.removeGlobalAction('print');
			component.removeGlobalAction('search');
			if (!composite) {
				component.removeGlobalAction('create');
				component.removeGlobalAction('save');
				component.removeGlobalAction('cancel');
				component.addGlobalAction({
					name: 'add',
					icon: 'glyphicon-send',
					displayName: 'Add',
					show: true
				}, function () {
					Backbone.trigger(component.componentId + '-toolbar-add');
				});
			}
        },
        getChilds: function(name){
			for (var idx in this.childComponents) {
				if (this.childComponents[idx].name === name) {
					return this.childComponents[idx].getRecords();
				}
			}
		},
		setChilds: function(childName,childData){
			for (var idx in this.childComponents) {
				if (this.childComponents[idx].name === childName) {
					this.childComponents[idx].setRecords(childData);
				}
			}
		},
		renderMaster: function(domElementId){
			this.masterComponent.render(domElementId);
		},
		renderChild: function(childName, domElementId){
			for (var idx in this.childComponents) {
				if (this.childComponents[idx].name === childName) {
					this.childComponents[idx].render(domElementId);
				}
			}
		}
    });

    return App.Component._EstadisticasMasterComponent;
});