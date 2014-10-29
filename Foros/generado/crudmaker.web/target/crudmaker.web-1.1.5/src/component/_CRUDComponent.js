define(['controller/messageController', 'component/toolbarComponent', 'component/listComponent'], function(Messages) {
    App.Component.BasicComponent = function() {

    };
    App.Component.BasicComponent.extend = Backbone.View.extend;

    App.Component._CRUDComponent = App.Component.BasicComponent.extend({
        initialize: function(options) {
            var self = this;
            this.componentId = App.Utils.randomInteger();
            this.pageSize = 25;
            this.showToolbar = true;
            this.showList = true;
            this.originalModelClass = this.modelClass;
            this.originalListModelClass = this.listModelClass;
            this.configuration = App.Utils.loadComponentConfiguration(this.name);
            App.Utils.loadTemplate(this.name);
            this.modelClass.prototype.urlRoot = this.configuration.context;
            this.listModelClass.prototype.url = this.configuration.context;
            this.el = this.configuration.el;
            if (options && options.cache) {
                this.configCache(options.cache);
            }
            this.componentController = new this.controller({modelClass: this.modelClass, listModelClass: this.listModelClass, componentId: this.componentId, pageSize: this.pageSize});
            this.toolbarComponent = new App.Component.ToolbarComponent({componentId: this.componentId, name: this.name});
            this.listComponent = new App.Component.ListComponent({componentId: this.componentId, name: this.name});
            if (options && typeof (options.pagination) === "boolean") {
                this.listComponent.setData({pagination: options.pagination});
            }
            Backbone.on(self.componentController.componentId + '-post-' + self.name + '-save', function(params) {
                self.toolbarComponent.hideButton('save');
                self.toolbarComponent.hideButton('cancel');
                self.toolbarComponent.render();
                self.componentController.list(params, self.list, self);
                var messagesController = new Messages({el: '#' + self.messageDomId});
                messagesController.showMessage('info', 'The ' + self.name + ' has been successfully saved.', true, 3);
            });
            Backbone.on(self.componentController.componentId + '-post-' + self.name + '-delete', function(params) {
                self.componentController.list(params, self.list, self);
                var messagesController = new Messages({el: '#' + self.messageDomId});
                messagesController.showMessage('info', 'The ' + self.name + ' has been successfully deleted.', true, 3);
            });
            Backbone.on(this.componentId + '-' + this.name + '-changePage', function(params) {
                self.componentController.setPage(params.page);
                self.componentController.list(null, self.list, self);
            });
            Backbone.on(self.componentController.componentId + '-error', function(params) {
                var messagesController = new Messages({el: '#' + self.messageDomId});
                var error = '';
                if (params.error) {
                    error = params.error.responseText;
                } else {
                    error = params.message;
                }
                messagesController.showMessage('danger', 'Error executing ' + params.event + ". Message: " + error, true);
            });

            this.loadToolbar();
            this.loadListActions();

            if(this.configUI){
                this.configUI();
            }
            
            if (this.postInit) {
                this.postInit();
            }            
        },
        configCache: function(params) {
            this.cacheMode = true;
            if (params && params.mode === "memory") {
                var models = App.Utils.convertToModel(App.Utils.createCacheModel(this.modelClass), params.data);
                this.modelClass = App.Utils.createCacheModel(this.modelClass);
                this.listModelClass = App.Utils.createCacheList(this.modelClass, this.listModelClass, models);
            } else {
                this.modelClass = this.originalModelClass;
                this.listModelClass = this.originalListModelClass;
            }
        },
        render: function(domElementId) {
            var self = this;
            if (domElementId) {
                var rootElement = $('#' + domElementId);
                this.toolbarDomId = this.componentId + "-" + domElementId + "-toolbar";
                this.messageDomId = this.componentId + "-" + domElementId + "-messages";
                this.contentDomId = this.componentId + "-" + domElementId + "-content";
                rootElement.append("<div id=" + this.toolbarDomId + "></div>");
                rootElement.append("<div id=" + this.messageDomId + "></div>");
                rootElement.append("<div id=" + this.contentDomId + "></div>");
                this.toolbarComponent.toolbarController.setElement('#' + this.toolbarDomId);
                this.componentController.setElement('#' + this.contentDomId);
            }
            if (this.componentController._loadRequiredComponentsData) {
                this.componentController._loadRequiredComponentsData(function() {
                    if (self.showToolbar) {
                        self.toolbarComponent.render();
                    }
                    if (self.showList) {
                        self.componentController.list(null, self.list, self);
                    }
                });
            } else {
                if (this.showToolbar) {
                    this.toolbarComponent.render();
                }
                if (this.showList) {
                    this.componentController.list(null, this.list, this);
                }
            }
        },
        create: function() {
            this.toolbarComponent.showButton('save');
            this.toolbarComponent.showButton('cancel');
            this.toolbarComponent.render();
            this.componentController.create();
        },
        save: function(params) {
            this.componentController.save();
        },
        cancel: function(params) {
            this.toolbarComponent.hideButton('save');
            this.toolbarComponent.hideButton('cancel');
            this.toolbarComponent.render();
            this.componentController.list(params, this.list, this);
        },
        refresh: function(params) {
            this.componentController.setPage(1);
            this.toolbarComponent.hideButton('save');
            this.toolbarComponent.hideButton('cancel');
            this.toolbarComponent.render();
            this.componentController.list(params, this.list, this);
            var messagesController = new App.Controller.MessageController({el: '#' + this.messageDomId});
            messagesController.showMessage('info', 'Data updated', true, 3);
        },
        search: function() {

        },
        print: function() {
            window.print();
        },
        edit: function(params) {
            this.toolbarComponent.showButton('save');
            this.toolbarComponent.showButton('cancel');
            this.toolbarComponent.render();
            this.componentController.edit(params);
        },
        delete: function(params) {
            this.componentController.destroy(params);
        },
        list: function(params) {
            this.currentPage = params.page;
            this.pages = params.pages;
            this.totalRecords = params.totalRecords;
            this.listComponent.setData(params);
            this.listComponent.changeElement('#' + this.contentDomId);
            this.listComponent.render();
        },
        loadToolbar: function() {
            this.toolbarComponent.addMenu({
                name: 'actions',
                displayName: 'Actions',
                show: true
            });

            this.toolbarComponent.addButton({
                name: 'create',
                icon: 'glyphicon-plus',
                displayName: 'Create',
                show: true
            },
            this.create,
                    this);

            this.toolbarComponent.addButton({
                name: 'save',
                icon: 'glyphicon-floppy-disk',
                displayName: 'Save',
                show: false
            },
            this.save,
                    this);

            this.toolbarComponent.addButton({
                name: 'cancel',
                icon: 'glyphicon-remove-sign',
                displayName: 'Cancel',
                show: false
            },
            this.cancel,
                    this);

            this.toolbarComponent.addMenu({
                name: 'utils',
                displayName: 'Utilities',
                show: true
            });

            this.toolbarComponent.addButton({
                name: 'refresh',
                icon: 'glyphicon-refresh',
                displayName: 'Refresh',
                show: true,
                menu: 'utils'
            },
            this.refresh,
                    this);

            this.toolbarComponent.addButton({
                name: 'search',
                icon: 'glyphicon-search',
                displayName: 'Search',
                show: true,
                menu: 'utils'
            },
            this.search,
                    this);

            this.toolbarComponent.addButton({
                name: 'print',
                icon: 'glyphicon-print',
                displayName: 'Print',
                show: true,
                menu: 'utils'
            },
            this.print,
                    this);
        },
        loadListActions: function() {
            this.listComponent.addAction({
                name: 'edit',
                icon: '',
                displayName: 'Edit',
                show: true
            },
            this.edit,
                    this);

            this.listComponent.addAction({
                name: 'delete',
                icon: '',
                displayName: 'Delete',
                show: true
            },
            this.delete,
                    this);
        },
        setRecordsByPage: function(pageSize) {
            this.pageSize = pageSize;
            this.componentController.setPageSize(pageSize);
        },
        setGlobalActionsVisible: function(flag) {
            if (typeof (flag) === "boolean") {
                this.showToolbar = flag;
                this.toolbarComponent.display(flag);
            } else {
                console.log("parameter value must be boolean type");
            }
        },
        setRecordsVisible: function(flag) {
            if (typeof (flag) === "boolean") {
                this.showList = flag;
                this.listComponent.display(flag);
            } else {
                console.log("parameter value must be boolean type");
            }
        },
        clearGlobalActions: function() {
            this.toolbarComponent.removeButton("create");
            this.toolbarComponent.removeButton("cancel");
            this.toolbarComponent.removeButton("save");
            this.toolbarComponent.removeButton("refresh");
            this.toolbarComponent.removeButton("print");
            this.toolbarComponent.removeButton("search");
        },
        clearRecordActions: function() {
            this.listComponent.removeAction("edit");
            this.listComponent.removeAction("delete");
        },
        setReadOnly: function(flag) {
            if (typeof (flag) === 'boolean') {
                if (flag) {
                    this.listComponent.hideAction("edit");
                    this.listComponent.hideAction("delete");
                    this.toolbarComponent.hideButton("create");
                    this.toolbarComponent.hideButton("cancel");
                    this.toolbarComponent.hideButton("save");
                } else {
                    this.listComponent.showAction("edit");
                    this.listComponent.showAction("delete");

                    this.toolbarComponent.showButton("create");
                    this.toolbarComponent.showButton("cancel");
                    this.toolbarComponent.showButton("save");
                }
            } else {
                console.log("parameter value must be boolean type");
            }
        },
        disableEdit: function() {
            this.listComponent.removeAction("edit");
        },
        disableDelete: function() {
            this.listComponent.removeAction("edit");
        },
        renderRecords: function() {
            this.listComponent.render();
        },
        addRecordAction: function(button, callback, context) {
            this.listComponent.addAction(button, callback, context);
        },
        addGlobalAction: function(button, callback, context) {
            this.toolbarComponent.addButton(button, callback, context);
        },
        removeGlobalAction: function(name) {
            this.toolbarComponent.removeButton(name);
        },
        removeRecordAction: function(name) {
            this.listComponent.removeAction(name);
        },
        enableMultipleSelection: function(flag) {
            if (typeof (flag) === "boolean") {
                this.listComponent.enableMultipleSelection(flag);
            } else {
                console.log("Parameter value must be boolean type");
            }
        },
        clearSelectedRecords: function() {
            this.listComponent.clearSelectedRecords();
        },
        getRecords: function() {
            return this.componentController.getRecords();
        },
        getDeletedRecords: function() {
            return this.componentController.getDeletedRecords();
        },
        getUpdatedRecords: function() {
            return this.componentController.getUpdatedRecords();
        },
        getCreatedRecords: function() {
            return this.componentController.getCreatedRecords();
        },
        getSelectedRecords: function() {
            return this.listComponent.getSelectedItems();
        },
        updateRecord: function(record) {
            this.componentController.updateRecord(record);
        },
        addRecords: function(objArray) {
            this.componentController.addRecords(objArray);
        },
        setRecords: function(records) {
            this.componentController.setRecords(records);
            this.listComponent.setData({data: this.componentController.currentList});
            this.listComponent.render();
        },
        changeCacheMode: function(params) {
            this.configCache(params);
            this.componentController = new this.controller({modelClass: this.modelClass, listModelClass: this.listModelClass, componentId: this.componentId, pageSize: this.pageSize});
        },
        clearCache: function() {
            this.componentController.clearCache();
        }
    });
    return App.Component._CRUDComponent;
});