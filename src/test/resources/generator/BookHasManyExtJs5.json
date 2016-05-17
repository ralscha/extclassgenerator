Ext.define("MyApp.Book",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct", "Ext.data.identifier.Uuid" ],
  identifier : "uuid",
  idProperty : "isbn2",
  versionProperty : "version2",
  clientIdProperty : "clientId2",
  fields : [ {
    name : "isbn2",
    type : "string"
  }, {
    name : "version2",
    type : "string"
  }, {
    name : "clientId2",
    type : "string"
  }, {
    name : "title",
    type : "string"
  }, {
    name : "publisher",
    type : "string"
  }, {
    name : "isbn",
    type : "string"
  }, {
    name : "publishDate",
    type : "date",
    dateFormat : "d-m-Y"
  }, {
    name : "numberOfPages",
    type : "integer"
  }, {
    name : "read",
    type : "boolean"
  } ],
  hasMany : [ "Author" ],
  proxy : {
    type : "direct",
    idParam : "isbn2",
    writer : {
      writeAllFields : true,
      clientIdProperty : "clientId2"
    }
  }
});