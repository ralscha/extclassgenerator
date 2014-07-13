Ext.define("ReadWrite",
{
  extend : "Ext.data.Model",
  requires : [ "Ext.data.proxy.Direct" ],
  fields : [ "id", "name" ],
  proxy : {
    type : "direct",
    reader : {
      type : "myreader"
    },
    writer : {
      type : "mywriter",
      writeAllFields : true
    }
  }
});