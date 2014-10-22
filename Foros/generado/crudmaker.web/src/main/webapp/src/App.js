define([],function() {
    App = {
        initialize: function(config) {
            
            Backbone.trigger('app-initialize', this);
        },
        components: []
    };

    App.Component = App.Module || {};
    App.Controller = App.Controller || {};
    App.Delegate = App.Delegate || {};
    App.Model = App.Model || {};
    App.Collection = App.Collection || {};
    App.Router = App.Router || {};
    
    return App;
});
