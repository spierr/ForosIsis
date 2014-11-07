define(['model/_faseMasterModel'], function() { 
    App.Model.FaseMasterModel = App.Model._FaseMasterModel.extend({

    });

    App.Model.FaseMasterList = App.Model._FaseMasterList.extend({
        model: App.Model.FaseMasterModel
    });

    return  App.Model.FaseMasterModel;

});