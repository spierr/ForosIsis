define(['controller/selectionController', 'model/cacheModel', 'model/foroMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/foroComponent',
 'component/faseComponent', 'component/coordinadorComponent', 'component/diaComponent'],
 function(SelectionController, CacheModel, ForoMasterModel, CRUDComponent, TabController, ForoComponent,
 fase_foroComponent, coordinador_foroComponent, dia_foroComponent) {
    App.Component._ForoMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('foroMaster');
            App.Model.ForoMasterModel.prototype.urlRoot = this.configuration.context;
            this.componentId = App.Utils.randomInteger();
            
            this.masterComponent = new ForoComponent();
            this.masterComponent.initialize();
            
            this.childComponents = [];
			
			this.initializeChildComponents();
            
            Backbone.on(this.masterComponent.componentId + '-post-foro-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(this.masterComponent.componentId + '-post-foro-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(this.masterComponent.componentId + '-pre-foro-list', function() {
                self.hideChilds();
            });
            Backbone.on('foro-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'foro-master-save', view: self, message: error});
            });
            Backbone.on(this.masterComponent.componentId + '-instead-foro-save', function(params) {
                self.model.set('foroEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }

				App.Utils.fillCacheList(
					'fase_foro',
					self.model,
					self.fase_foroComponent.getDeletedRecords(),
					self.fase_foroComponent.getUpdatedRecords(),
					self.fase_foroComponent.getCreatedRecords()
				);

				App.Utils.fillCacheList(
					'coordinador_foro',
					self.model,
					self.coordinador_foroComponent.getDeletedRecords(),
					self.coordinador_foroComponent.getUpdatedRecords(),
					self.coordinador_foroComponent.getCreatedRecords()
				);

				App.Utils.fillCacheList(
					'dia_foro',
					self.model,
					self.dia_foroComponent.getDeletedRecords(),
					self.dia_foroComponent.getUpdatedRecords(),
					self.dia_foroComponent.getCreatedRecords()
				);

                self.model.save({}, {
                    success: function() {
                        Backbone.trigger(self.masterComponent.componentId + '-' + 'post-foro-save', {view: self, model : self.model});
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'foro-master-save', view: self, error: error});
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
                {label: "Fase_foro", name: "fase_foro", enable: true},
                {label: "Coordinador_foro", name: "coordinador_foro", enable: true},
                {label: "Dia_foro", name: "dia_foro", enable: true}
			]});
			this.tabs = new TabController({model: this.tabModel});

			this.fase_foroComponent = new fase_foroComponent();
            this.fase_foroComponent.initialize({cache: {data: [], mode: "memory"},pagination: false});
			this.childComponents.push(this.fase_foroComponent);

			this.coordinador_foroComponent = new coordinador_foroComponent();
            this.coordinador_foroComponent.initialize({cache: {data: [], mode: "memory"},pagination: false});
			this.childComponents.push(this.coordinador_foroComponent);

			this.dia_foroComponent = new dia_foroComponent();
            this.dia_foroComponent.initialize({cache: {data: [], mode: "memory"},pagination: false});
			this.childComponents.push(this.dia_foroComponent);

            var self = this;
            
            this.configToolbar(this.fase_foroComponent,true);
            Backbone.on(self.fase_foroComponent.componentId + '-post-fase-create', function(params) {
                params.view.currentModel.setCacheList(params.view.currentList);
            });
            
            this.configToolbar(this.coordinador_foroComponent,true);
            Backbone.on(self.coordinador_foroComponent.componentId + '-post-coordinador-create', function(params) {
                params.view.currentModel.setCacheList(params.view.currentList);
            });
            
            this.configToolbar(this.dia_foroComponent,true);
            Backbone.on(self.dia_foroComponent.componentId + '-post-dia-create', function(params) {
                params.view.currentModel.setCacheList(params.view.currentList);
            });
            
		},
        renderChilds: function(params) {
            var self = this;
            
            var options = {
                success: function() {
                	self.tabs.render(self.tabsElement);

					self.fase_foroComponent.clearCache();
					self.fase_foroComponent.setRecords(self.model.get('listfase_foro'));
					self.fase_foroComponent.render(self.tabs.getTabHtmlId('fase_foro'));

					self.coordinador_foroComponent.clearCache();
					self.coordinador_foroComponent.setRecords(self.model.get('listcoordinador_foro'));
					self.coordinador_foroComponent.render(self.tabs.getTabHtmlId('coordinador_foro'));

					self.dia_foroComponent.clearCache();
					self.dia_foroComponent.setRecords(self.model.get('listdia_foro'));
					self.dia_foroComponent.render(self.tabs.getTabHtmlId('dia_foro'));

                    $('#'+self.tabsElement).show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'foro-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.ForoMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.ForoMasterModel();
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

    return App.Component._ForoMasterComponent;
});