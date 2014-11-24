define(['model/_estadisticasMasterModel'], function() { 
    App.Model.EstadisticasMasterModel = App.Model._EstadisticasMasterModel.extend({

    });

    App.Model.EstadisticasMasterList = App.Model._EstadisticasMasterList.extend({
        model: App.Model.EstadisticasMasterModel
    });

    return  App.Model.EstadisticasMasterModel;

});