define(['model/_coordinadorMasterModel'], function() { 
    App.Model.CoordinadorMasterModel = App.Model._CoordinadorMasterModel.extend({

    });

    App.Model.CoordinadorMasterList = App.Model._CoordinadorMasterList.extend({
        model: App.Model.CoordinadorMasterModel
    });

    return  App.Model.CoordinadorMasterModel;

});