define([], function() {
    App.Model._CoordinadorMasterModel = Backbone.Model.extend({
     	initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('coordinador-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.CoordinadorModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.coordinadorEntity,options);
            }
        }
    });

    App.Model._CoordinadorMasterList = Backbone.Collection.extend({
        model: App.Model._CoordinadorMasterModel,
        initialize: function() {
        }

    });
    return App.Model._CoordinadorMasterModel;
    
});