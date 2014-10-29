define(['model/_userMasterModel'], function() { 
    App.Model.UserMasterModel = App.Model._UserMasterModel.extend({

    });

    App.Model.UserMasterList = App.Model._UserMasterList.extend({
        model: App.Model.UserMasterModel
    });

    return  App.Model.UserMasterModel;

});