define([], function() {
    App.Controller.ToolbarController = Backbone.View.extend({
        initialize: function() {
            App.Utils.loadTemplate('toolbar');
            this.template = _.template($('#toolbar-template').html());
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

            Backbone.trigger(this.model.get('componentId') + '-toolbar-create', event, this);
        },
        save: function(event) { 
            Backbone.trigger(this.model.get('componentId') + '-toolbar-save', event, this);
        },
        cancel: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-toolbar-cancel', event, this);
        },
        refresh: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-toolbar-refresh', event, this);
        },
        search: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-toolbar-search', event, this);
        },
        print: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-toolbar-print', event, this);
        }, 
        add: function(event) {
            Backbone.trigger(this.model.get('componentId') + '-toolbar-add', event, this);
        }
    });

    return App.Controller.ToolbarController;

});