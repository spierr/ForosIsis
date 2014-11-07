define(['model/_foroMasterModel'], function() { 
    App.Model.ForoMasterModel = App.Model._ForoMasterModel.extend({

    });

    App.Model.ForoMasterList = App.Model._ForoMasterList.extend({
        model: App.Model.ForoMasterModel
    });

    return  App.Model.ForoMasterModel;

});