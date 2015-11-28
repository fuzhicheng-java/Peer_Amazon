
import java.util.Arrays;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class Table {

	public AmazonDynamoDBClient client;

	public DynamoDB dynamoDB;

	public com.amazonaws.services.dynamodbv2.document.Table table;

	// private Hashtable<Long, String> table;

	public Table() {
		super();
		String tableName = "shareFiles";
		this.client = new AmazonDynamoDBClient();
		this.client.setEndpoint("http://localhost:8000");
		this.dynamoDB = new DynamoDB(client);
		this.table=this.dynamoDB.getTable(tableName);
		if(this.table==null)
		{
		this.table = this.dynamoDB.createTable(tableName, Arrays
				.asList(new KeySchemaElement("key", KeyType.HASH)), // Sort key
				Arrays.asList(new AttributeDefinition("key",
						ScalarAttributeType.N)), new ProvisionedThroughput(10L,
						10L));
		}
	}

	public boolean put(long key, String value) {
		// table.put(key, value);
		table.putItem(new Item().withPrimaryKey("key", key)
				.withString("value", value));
		System.out.println("PutItem succeeded: " + 
	            table.getItem("key", key).toJSONPretty());
		return true;
	}

	public String get(long key) {
		return table.getItem("key", key).toJSONPretty();
		// return table.get(key);
	}

	public boolean del(long key) {
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
				.withPrimaryKey(new PrimaryKey("key", key));

		System.out.println("Attempting a conditional delete...");

		table.deleteItem(deleteItemSpec);
		// table.remove(key);

		return true;
	}

	public com.amazonaws.services.dynamodbv2.document.Table getTable() {
		return table;
	}

	public void setTable(com.amazonaws.services.dynamodbv2.document.Table table) {
		this.table = table;
	}

}
