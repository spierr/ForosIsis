define(['model/_diaMasterModel'], function() { 
    App.Model.DiaMasterModel = App.Model._DiaMasterModel.extend({

    });

    App.Model.DiaMasterList = App.Model._DiaMasterList.extend({
        model: App.Model.DiaMasterModel
    });

    return  App.Model.DiaMasterModel;

});