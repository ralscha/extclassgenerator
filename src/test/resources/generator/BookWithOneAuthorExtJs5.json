Ext.define("ch.rasc.extclassgenerator.bean.BookWithOneAuthor",
{
  extend : "Ext.data.Model",
  uses : [ "MyApp.Author" ],
  fields : [ {
    name : "id",
    type : "integer"
  }, {
    name : "author_id",
    type : "integer"
  } ],
  associations : [ {
    type : "hasOne",
    model : "MyApp.Author",
    associationKey : "author",
    foreignKey : "author_id",
    setterName : "setAuthor",
    getterName : "getAuthor"
  } ]
});