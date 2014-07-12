Ext.define("MyApp.Employee",
{
  extend : "Ext.data.Model",
  uses : [ "ch.rasc.extclassgenerator.bean.Address" ],
  requires : [ "Ext.data.proxy.Direct" ],
  idProperty : "eId",
  versionProperty : "version",
  fields : [ {
    name : "eId",
    type : "integer"
  }, {
    name : "version",
    type : "integer"
  }, {
    name : "address_id",
    type : "integer"
  } ],
  associations : [ {
    type : "hasOne",
    model : "ch.rasc.extclassgenerator.bean.Address",
    associationKey : "address",
    foreignKey : "address_id",
    instanceName : "addressBelongsToInstance",
    setterName : "setAddress",
    getterName : "getAddress"
  } ],
  proxy : {
    type : "direct",
    idParam : "eId",
    writer : {
      writeAllFields : true
    }
  }
});