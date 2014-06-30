Ext.define("Sch.Bean3",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ {
    name : "id",
    type : "string"
  }, {
    name : "name",
    type : "string"
  } ],
  proxy : {
    type : "direct",
    directFn : "read",
    reader : {
      rootProperty : "theRootProperty",
      messageProperty : "theMessageProperty",
      totalProperty : "theTotalProperty",
      successProperty : "theSuccessProperty"
    },
    writer : {
      writeAllFields : true
    }
  }
});