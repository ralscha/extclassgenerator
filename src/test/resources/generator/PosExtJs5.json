Ext.define("ch.rasc.extclassgenerator.bean.Pos",
{
  extend : "Ext.data.Model",
  uses : [ "ch.rasc.extclassgenerator.bean.Order" ],
  fields : [ {
    name : "entityId",
    type : "integer"
  }, {
    name : "orderId",
    type : "integer"
  } ],
  associations : [ {
    type : "belongsTo",
    model : "ch.rasc.extclassgenerator.bean.Order",
    associationKey : "order",
    foreignKey : "orderId",
    primaryKey : "entityId",
    setterName : "setTheOrder",
    getterName : "getMeTheOrder"
  } ]
});