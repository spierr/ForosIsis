define(['App', 'controller/messageController'], function(App, Messages) {
    String.prototype.firstToUpperCase = function() {
        return this.substr(0, 1).toUpperCase() + this.substr(1);
    };
    String.prototype.firstToLowerCase = function() {
        return this.substr(0, 1).toLowerCase() + this.substr(1);
    };
    window.htmlEncode = function(value) {
        return $('<div/>').text(value).html();
    };

    window.htmlDecode = function(value) {
        return $('<div/>').html(value).text();
    };

    Backbone.on('error', function() {
        var messagesController = new Messages({el: 'messsages'});
        messagesController.showMessage('success', 'Error', true, 5);
    });

    $.fn.serializeObject = function() {
        var object = {};
        var form = this;
        var a = this.serializeArray();
        $.each(a, function() {
            if (object[this.name] !== undefined) {
                if (!object[this.name].push) {
                    object[this.name] = [object[this.name]];
                }
                object[this.name].push(this.value || '');
            } else {
                var converterName = $(form).find(':input[name="' + this.name + '"]').attr('data-converter');
                if (converterName) {
                    var converter = App.Utils.Converter[converterName];
                    object[this.name] = converter.serialize(this.value, this);
                } else {
                    object[this.name] = this.value || '';
                }
            }
        });
        return object;
    };
    //Initialize utils
    App.Utils = App.Utils || {
        cache: {},
        /*
         *  
         */
        randomInteger: function() {
            return  _.random(1001, 100000) + "";
        },
        convertToModel: function(modelClass, objectList) {
            var models = [];
            if (objectList) {
                for (var i = 0; i < objectList.length; i++) {
                    models.push(new modelClass(objectList[i]));
                }
            }
            return models;
        },
        createCacheModel: function(modelClass) {
            return modelClass.extend(App.Model.CacheModel);
        },
        createCacheList: function(modelClass, listClass, initialList) {
            //var cacheModel = this.createCacheModel(modelClass);
            var listModel = App.Model.CacheListModel;
            listModel.model = modelClass;
            listModel.deletedModels = [];
            listModel.cacheModels = initialList;
            return listClass.extend(listModel);
        },
		fillCacheList: function (name, destModel, deletedRecords, updatedRecords, createdRecords) {
			destModel.set('list' + name, []);
			destModel.set('create' + name, createdRecords);
			destModel.set('update' + name, updatedRecords);
			destModel.set('delete' + name, deletedRecords);
		},
        getComponentList: function(componentName, callBack, aliasName) {
            var self = this;
            if (!this.cache[componentName]) {
                require(["component/" + componentName], function(componentDef) {
                    var component = new componentDef();
                    component.initialize();
                    var model = new component.listModelClass();
                    model.fetch({
                        success: function() {
                            self.cache[componentName] = model;
                            callBack(componentName, model, aliasName);
                        },
                        error: function(mode, error) {
                            Backbone.trigger(self.componentId + '-' + 'error', {event: 'cache-list', view: self, error: error});
                        }
                    });
                });
            } else {
                callBack(componentName, this.cache[componentName], aliasName);
            }
        },
        getModelFromCache: function(componentName, id) {
            var model;
            var listModel = this.cache[componentName];
            if (listModel) {
                listModel.each(function(modeli) {
                    if (modeli.get('id') == id) {
                        model = modeli;
                    }
                });
            }
            return model;
        },
        eventExists: function(eventName) {
            if (Backbone._events) {
                return !!Backbone._events[eventName];
            } else {
                return false;
            }
        },
        loadComponents: function() {
            var components = [];
            for (var i = 0; i < loadComponents.length; i++) {
                components[i] = loadComponents[i].name;
            }
            require(components, function() {
                for (var i = 0; i < arguments.length; i++) {
                    var component = new arguments[i]();
                    App.components.push(component);
                    component.initialize();
                    if (loadComponents[i].render) {
                        component.render(loadComponents[i].domElementId);
                    }
                }
                try {
                    Backbone.history.start();
                } catch (exception) {

                }
            });
        },
        loadComponentConfiguration: function(view) {
            var confiData;
            $.ajax({
                dataType: "json",
                url: './src/component/config/' + view + 'Config.json',
                async: false,
                success: function(data) {
                    confiData = data;
                }
            });
            return confiData;
        },
        initializeView: function(view) {
            try {
                eval('new App.Router.' + view.firstToUpperCase() + 'Router()');
            } catch (ex) {

            }
            App.Utils.loadTemplate(view);
            Backbone.history.start();
        },
        getTemplate: function(view, id) {
            var tmpl_string = '';
            $.ajax({
                url: './src/view/' + view + 'Templates.html',
                method: 'GET',
                async: false,
                contentType: 'text/html',
                dataType: 'html',
                success: function(data) {
                    tmpl_string = data;
                }
            });
            var x = $('<div></div>').append(tmpl_string);
            return $(id, x).html();
        },
        loadTemplate: function(view) {

            $.ajax({
                url: './src/view/' + view + 'Templates.html',
                method: 'GET',
                async: false,
                contentType: 'text/html',
                dataType: 'html',
                success: function(data) {
                    $('body').append($(data));
                }
            });
        },
        loadConfiguration: function(name, callback) {
            $.getJSON('config.json', callback(data));
        },
        handleErrors: function(model, error) {
            alert(error);
        }
    };
    App.Utils.Constans = {
        containmentToolbarConfiguration: {
            showTitle: false,
            showCreate: false,
            showSave: false,
            showCancel: false,
            showRefresh: false,
            showSearch: false,
            showPrint: false,
            showAddButton: true,
            addName: 'Add',
            showComponentList: false
        },
        referenceToolbarConfiguration: {
            showTitle: false,
            showSearch: false,
            showPrint: false,
            showRefresh: false,
            showComponentList: false
        }

    };
    App.Utils.Converter = {
        date: {
            serialize: function(value) {
                //var parts = value.split("/");
                //var dat = new Date(parts[2], parts[1] - 1, parts[0]);
                return value;
            },
            unserialize: function(value) {
                //var dat = new Date(value);
                //var dateParts = value.split("/");
//                return (dat.getUTCDate()) + "/" + (dat.getUTCMonth() + 1) + "/" + dat.getFullYear();
                return value;
            },
            toDate: function(stringDate) {
                if (stringDate && stringDate.length > 8) {
                    var dateParts = stringDate.split("/");
                    // Year / Month / day
                    return new Date(dateParts[2], parseInt(dateParts[1]) - 1, dateParts[0]);
                } else {
                    return new Date();
                }
            }
        },
        boolean: function(value, object) {
            if (value) {
                return true;
            } else {
                return false;
            }
        }
    };
    return App.Utils;
});