define([], function() {
    App.Model._FaseMasterModel = Backbone.Model.extend({
     	initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('fase-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.FaseModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.faseEntity,options);
            }
        }
    });

    App.Model._FaseMasterList = Backbone.Collection.extend({
        model: App.Model._FaseMasterModel,
        initialize: function() {
        }

    });
    return App.Model._FaseMasterModel;
    
});