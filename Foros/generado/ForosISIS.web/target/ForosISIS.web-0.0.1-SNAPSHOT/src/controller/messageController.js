define([], function() {
    App.Controller.MessageController = Backbone.View.extend({
        initialize: function() {
            App.Utils.loadTemplate('message');
            this.template = _.template($('#message-template').html());
            if (!this.componentId) {
                this.componentId = _.random(0, 100) + "";
            }
        },
        _render: function() {
            this.$el.html(this.template(this.model));
        },
        showMessage: function(type, message, dismissable, delay) {
            this.model = {type: type, message: message, dismissable: dismissable, delay: delay, componentId : this.componentId};
            this._render();
        }
    });

    return App.Controller.MessageController;
});