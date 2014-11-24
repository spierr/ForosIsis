define([], function() {
    App.Model._EstadisticasMasterModel = Backbone.Model.extend({
     	initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('estadisticas-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.EstadisticasModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.estadisticasEntity,options);
            }
        }
    });

    App.Model._EstadisticasMasterList = Backbone.Collection.extend({
        model: App.Model._EstadisticasMasterModel,
        initialize: function() {
        }

    });
    return App.Model._EstadisticasMasterModel;
    
});