// Copyright (c) 2014, Cloudera, inc.
// Confidential Cloudera Information: Covered by NDA.
package kudu.rpc;

import java.util.List;

public class ListTablesResponse extends KuduRpcResponse {

  private final List<String> tablesList;

  ListTablesResponse(long ellapsedMillis, String tsUUID, List<String> tablesList) {
    super(ellapsedMillis, tsUUID);
    this.tablesList = tablesList;
  }

  /**
   * Get the list of tables as specified in the request.
   * @return a list of table names
   */
  public List<String> getTablesList() {
    return tablesList;
  }
}
