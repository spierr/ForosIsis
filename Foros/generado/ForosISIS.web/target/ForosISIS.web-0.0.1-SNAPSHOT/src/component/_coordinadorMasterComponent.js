define(['controller/selectionController', 'model/cacheModel', 'model/coordinadorMasterModel', 'component/_CRUDComponent', 'controller/tabController', 'component/coordinadorComponent',
 'component/tareaComponent'],
 function(SelectionController, CacheModel, CoordinadorMasterModel, CRUDComponent, TabController, CoordinadorComponent,
 tarea_coordinadorComponent) {
    App.Component._CoordinadorMasterComponent = App.Component.BasicComponent.extend({
        initialize: function() {
            var self = this;
            this.configuration = App.Utils.loadComponentConfiguration('coordinadorMaster');
            App.Model.CoordinadorMasterModel.prototype.urlRoot = this.configuration.context;
            this.componentId = App.Utils.randomInteger();
            
            this.masterComponent = new CoordinadorComponent();
            this.masterComponent.initialize();
            
            this.childComponents = [];
			
			this.initializeChildComponents();
            
            Backbone.on(this.masterComponent.componentId + '-post-coordinador-create', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(this.masterComponent.componentId + '-post-coordinador-edit', function(params) {
                self.renderChilds(params);
            });
            Backbone.on(this.masterComponent.componentId + '-pre-coordinador-list', function() {
                self.hideChilds();
            });
            Backbone.on('coordinador-master-model-error', function(error) {
                Backbone.trigger(uComponent.componentId + '-' + 'error', {event: 'coordinador-master-save', view: self, message: error});
            });
            Backbone.on(this.masterComponent.componentId + '-instead-coordinador-save', function(params) {
                self.model.set('coordinadorEntity', params.model);
                if (params.model) {
                    self.model.set('id', params.model.id);
                } else {
                    self.model.unset('id');
                }

				App.Utils.fillCacheList(
					'tarea_coordinador',
					self.model,
					self.tarea_coordinadorComponent.getDeletedRecords(),
					self.tarea_coordinadorComponent.getUpdatedRecords(),
					self.tarea_coordinadorComponent.getCreatedRecords()
				);

                self.model.save({}, {
                    success: function() {
                        Backbone.trigger(self.masterComponent.componentId + '-' + 'post-coordinador-save', {view: self, model : self.model});
                    },
                    error: function(error) {
                        Backbone.trigger(self.componentId + '-' + 'error', {event: 'coordinador-master-save', view: self, error: error});
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
                {label: "Tarea_coordinador", name: "tarea_coordinador", enable: true}
			]});
			this.tabs = new TabController({model: this.tabModel});

			this.tarea_coordinadorComponent = new tarea_coordinadorComponent();
            this.tarea_coordinadorComponent.initialize({cache: {data: [], mode: "memory"},pagination: false});
			this.childComponents.push(this.tarea_coordinadorComponent);

            var self = this;
            
            this.configToolbar(this.tarea_coordinadorComponent,false);
            this.tarea_coordinadorComponent.disableEdit();

            Backbone.on(this.tarea_coordinadorComponent.componentId + '-toolbar-add', function() {
                var selection = new SelectionController({"componentId":"tarea_coordinadorComponent"});
                App.Utils.getComponentList('tareaComponent', function(componentName, model) {
                    if (model.models.length == 0) {
                        alert('There is no Tarea_coordinadors to select.');
                    } else {
                        selection.showSelectionList({list: model, name: 'name', title: 'Tarea_coordinador List'});
                    }
                    ;
                });
            });

            Backbone.on('tarea_coordinadorComponent-post-selection', function(models) {
            	self.tarea_coordinadorComponent.addRecords(models);
            	self.tarea_coordinadorComponent.render();
            });

		},
        renderChilds: function(params) {
            var self = this;
            
            var options = {
                success: function() {
                	self.tabs.render(self.tabsElement);

					self.tarea_coordinadorComponent.clearCache();
					self.tarea_coordinadorComponent.setRecords(self.model.get('listtarea_coordinador'));
					self.tarea_coordinadorComponent.render(self.tabs.getTabHtmlId('tarea_coordinador'));

                    $('#'+self.tabsElement).show();
                },
                error: function() {
                    Backbone.trigger(self.componentId + '-' + 'error', {event: 'coordinador-edit', view: self, id: id, data: data, error: error});
                }
            };
            if (params.id) {
                self.model = new App.Model.CoordinadorMasterModel({id: params.id});
                self.model.fetch(options);
            } else {
                self.model = new App.Model.CoordinadorMasterModel();
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

    return App.Component._CoordinadorMasterComponent;
});