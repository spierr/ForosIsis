define([], function() {
    App.Controller.ToolbarController = Backbone.View.extend({
        initialize: function() {
            App.Utils.loadTemplate('tableModal');
            this.template = _.template($('#tableModal-template').html());
            this.events = {};
            this.events['click #' + this.model.get('componentId') + '-createButton'] = 'create';
            this.events['click #' + this.model.get('componentId') + '-saveButton'] = 'save';
            this.events['click #' + this.model.get('componentId') + '-cancelButton'] = 'cancel';
            this.events['click #' + this.model.get('componentId') + '-refreshButton'] = 'refresh';
            this.events['click #' + this.model.get('componentId') + '-searchButton'] = 'search';
            this.events['click #' + this.model.get('componentId') + '-printButton'] = 'print';
            this.events['click #' + this.model.get('componentId') + '-addButton'] = 'add';
            this.listenTo(this.model, 'change', this.render);
        },
        render: function() {
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        },
        create: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-tableModal-create', event, this);
        },
        save: function(event) { 
            Backbone.trigger(this.model.get('componentId') + '-tableModal-save', event, this);
        },
        cancel: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-tableModal-cancel', event, this);
        },
        refresh: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-tableModal-refresh', event, this);
        },
        search: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-tableModal-search', event, this);
        },
        print: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-tableModal-print', event, this);
        }, 
        add: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-tableModal-add', event, this);
        }
    });

    return App.Controller.ToolbarController;

});