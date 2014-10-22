define(['model/tabModel'], function() {
    App.Controller.TabController = Backbone.View.extend({
        initialize: function() {
            if (!this.componentId) {
                this.componentId = _.random(0, 100) + "";
            }
            App.Utils.loadTemplate('tab');
            this.template = _.template($('#tab-template').html());
            this.events = {};
        },
        render: function(id) {  
            this.model.set('componentId', this.componentId);
            $('#' + id).html(this.template(this.model.toJSON()));
            return this; 
        },
        disableTab: function(tabName) {
            //TODO: 
        },
        getTabHtmlId: function(tabName) {
            return this.componentId + "-" + tabName;
        },
        setTabContent: function(tabName, content) {
            $('#' + this.getTabHtmlId(tabName)).html(content);
        },
        enableTab: function(tabName) {

        }, 
        disableAllTabs: function() {
            for (tab in this.model.tabs) {

            }
        },
        enableAllTabs: function() {

        },
        tabChanged: function() {

        },
        showTab: function(tabName) {

        }
    });

    return App.Controller.TabController;

});