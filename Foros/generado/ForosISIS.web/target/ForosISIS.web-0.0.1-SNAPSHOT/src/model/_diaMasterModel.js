define([], function() {
    App.Model._DiaMasterModel = Backbone.Model.extend({
     	initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('dia-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.DiaModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.diaEntity,options);
            }
        }
    });

    App.Model._DiaMasterList = Backbone.Collection.extend({
        model: App.Model._DiaMasterModel,
        initialize: function() {
        }

    });
    return App.Model._DiaMasterModel;
    
});