define([], function() {
    App.Model._ForoMasterModel = Backbone.Model.extend({
     	initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('foro-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.ForoModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.foroEntity,options);
            }
        }
    });

    App.Model._ForoMasterList = Backbone.Collection.extend({
        model: App.Model._ForoMasterModel,
        initialize: function() {
        }

    });
    return App.Model._ForoMasterModel;
    
});