Ext.define("ch.rasc.extclassgenerator.bean.Order",
{
  extend : "Ext.data.Model",
  uses : [ "ch.rasc.extclassgenerator.bean.Pos" ],
  fields : [ {
    name : "entityId",
    type : "integer"
  } ],
  associations : [ {
    type : "hasMany",
    model : "ch.rasc.extclassgenerator.bean.Pos",
    associationKey : "positions",
    foreignKey : "orderId",
    primaryKey : "entityId",
    name : "pos"
  } ]
});