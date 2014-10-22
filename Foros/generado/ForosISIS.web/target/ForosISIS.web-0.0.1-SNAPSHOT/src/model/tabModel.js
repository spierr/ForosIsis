define([], function() {
    App.Model.TabModel = Backbone.Model.extend({
        defaults: {
            componentId: '',
            name: '-',
            icon: 'globe',
            tabs : []
        },
        initialize: function() {

        }
    });
    return App.Model.TabModel;
});