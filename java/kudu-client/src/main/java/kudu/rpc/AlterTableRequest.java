// Copyright (c) 2014, Cloudera, inc.
// Confidential Cloudera Information: Covered by NDA.
package kudu.rpc;

import com.google.protobuf.Message;
import kudu.util.Pair;
import org.jboss.netty.buffer.ChannelBuffer;
import static kudu.master.Master.*;

/**
 * RPC used to alter a table. When it returns it doesn't mean that the table is altered,
 * a success just means that the master accepted it.
 */
class AlterTableRequest extends KuduRpc<AlterTableResponse> {

  static final String ALTER_TABLE = "AlterTable";
  private final String name;
  private final AlterTableRequestPB.Builder builder;

  AlterTableRequest(KuduTable masterTable, String name, AlterTableBuilder atb) {
    super(masterTable);
    this.name = name;
    this.builder = atb.pb;
  }

  @Override
  ChannelBuffer serialize(Message header) {
    assert header.isInitialized();
    TableIdentifierPB tableID =
        TableIdentifierPB.newBuilder().setTableName(name).build();
    this.builder.setTable(tableID);
    return toChannelBuffer(header, this.builder.build());
  }

  @Override
  String method() {
    return ALTER_TABLE;
  }

  @Override
  Pair<AlterTableResponse, Object> deserialize(ChannelBuffer buf, String tsUUID) throws Exception {
    final AlterTableResponsePB.Builder respBuilder = AlterTableResponsePB.newBuilder();
    readProtobuf(buf, respBuilder);
    AlterTableResponse response = new AlterTableResponse(deadlineTracker.getElapsedMillis(),
        tsUUID);
    return new Pair<AlterTableResponse, Object>(response, respBuilder.getError());
  }
}
