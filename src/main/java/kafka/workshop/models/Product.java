/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package kafka.workshop.models;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class Product extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -832475343777420570L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Product\",\"namespace\":\"kafka.workshop.models\",\"fields\":[{\"name\":\"id\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"name\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}],\"default\":null},{\"name\":\"price\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"create_ts\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},{\"name\":\"update_ts\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}],\"connect.name\":\"products\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();
static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
  }

  private static final BinaryMessageEncoder<Product> ENCODER =
      new BinaryMessageEncoder<Product>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Product> DECODER =
      new BinaryMessageDecoder<Product>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Product> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Product> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Product> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Product>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Product to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Product from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Product instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Product fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.Integer id;
  @Deprecated public java.lang.String name;
  @Deprecated public java.lang.Integer price;
  @Deprecated public java.time.Instant create_ts;
  @Deprecated public java.time.Instant update_ts;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Product() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param name The new value for name
   * @param price The new value for price
   * @param create_ts The new value for create_ts
   * @param update_ts The new value for update_ts
   */
  public Product(java.lang.Integer id, java.lang.String name, java.lang.Integer price, java.time.Instant create_ts, java.time.Instant update_ts) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.create_ts = create_ts.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
    this.update_ts = update_ts.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return name;
    case 2: return price;
    case 3: return create_ts;
    case 4: return update_ts;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: name = value$ != null ? value$.toString() : null; break;
    case 2: price = (java.lang.Integer)value$; break;
    case 3: create_ts = (java.time.Instant)value$; break;
    case 4: update_ts = (java.time.Instant)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.Integer getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'price' field.
   * @return The value of the 'price' field.
   */
  public java.lang.Integer getPrice() {
    return price;
  }


  /**
   * Sets the value of the 'price' field.
   * @param value the value to set.
   */
  public void setPrice(java.lang.Integer value) {
    this.price = value;
  }

  /**
   * Gets the value of the 'create_ts' field.
   * @return The value of the 'create_ts' field.
   */
  public java.time.Instant getCreateTs() {
    return create_ts;
  }


  /**
   * Sets the value of the 'create_ts' field.
   * @param value the value to set.
   */
  public void setCreateTs(java.time.Instant value) {
    this.create_ts = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Gets the value of the 'update_ts' field.
   * @return The value of the 'update_ts' field.
   */
  public java.time.Instant getUpdateTs() {
    return update_ts;
  }


  /**
   * Sets the value of the 'update_ts' field.
   * @param value the value to set.
   */
  public void setUpdateTs(java.time.Instant value) {
    this.update_ts = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Creates a new Product RecordBuilder.
   * @return A new Product RecordBuilder
   */
  public static kafka.workshop.models.Product.Builder newBuilder() {
    return new kafka.workshop.models.Product.Builder();
  }

  /**
   * Creates a new Product RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Product RecordBuilder
   */
  public static kafka.workshop.models.Product.Builder newBuilder(kafka.workshop.models.Product.Builder other) {
    if (other == null) {
      return new kafka.workshop.models.Product.Builder();
    } else {
      return new kafka.workshop.models.Product.Builder(other);
    }
  }

  /**
   * Creates a new Product RecordBuilder by copying an existing Product instance.
   * @param other The existing instance to copy.
   * @return A new Product RecordBuilder
   */
  public static kafka.workshop.models.Product.Builder newBuilder(kafka.workshop.models.Product other) {
    if (other == null) {
      return new kafka.workshop.models.Product.Builder();
    } else {
      return new kafka.workshop.models.Product.Builder(other);
    }
  }

  /**
   * RecordBuilder for Product instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Product>
    implements org.apache.avro.data.RecordBuilder<Product> {

    private java.lang.Integer id;
    private java.lang.String name;
    private java.lang.Integer price;
    private java.time.Instant create_ts;
    private java.time.Instant update_ts;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(kafka.workshop.models.Product.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.price)) {
        this.price = data().deepCopy(fields()[2].schema(), other.price);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.create_ts)) {
        this.create_ts = data().deepCopy(fields()[3].schema(), other.create_ts);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.update_ts)) {
        this.update_ts = data().deepCopy(fields()[4].schema(), other.update_ts);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
    }

    /**
     * Creates a Builder by copying an existing Product instance
     * @param other The existing instance to copy.
     */
    private Builder(kafka.workshop.models.Product other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.price)) {
        this.price = data().deepCopy(fields()[2].schema(), other.price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.create_ts)) {
        this.create_ts = data().deepCopy(fields()[3].schema(), other.create_ts);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.update_ts)) {
        this.update_ts = data().deepCopy(fields()[4].schema(), other.update_ts);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.Integer getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder setId(java.lang.Integer value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.String getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder setName(java.lang.String value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'price' field.
      * @return The value.
      */
    public java.lang.Integer getPrice() {
      return price;
    }


    /**
      * Sets the value of the 'price' field.
      * @param value The value of 'price'.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder setPrice(java.lang.Integer value) {
      validate(fields()[2], value);
      this.price = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'price' field has been set.
      * @return True if the 'price' field has been set, false otherwise.
      */
    public boolean hasPrice() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'price' field.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder clearPrice() {
      price = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'create_ts' field.
      * @return The value.
      */
    public java.time.Instant getCreateTs() {
      return create_ts;
    }


    /**
      * Sets the value of the 'create_ts' field.
      * @param value The value of 'create_ts'.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder setCreateTs(java.time.Instant value) {
      validate(fields()[3], value);
      this.create_ts = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'create_ts' field has been set.
      * @return True if the 'create_ts' field has been set, false otherwise.
      */
    public boolean hasCreateTs() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'create_ts' field.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder clearCreateTs() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'update_ts' field.
      * @return The value.
      */
    public java.time.Instant getUpdateTs() {
      return update_ts;
    }


    /**
      * Sets the value of the 'update_ts' field.
      * @param value The value of 'update_ts'.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder setUpdateTs(java.time.Instant value) {
      validate(fields()[4], value);
      this.update_ts = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'update_ts' field has been set.
      * @return True if the 'update_ts' field has been set, false otherwise.
      */
    public boolean hasUpdateTs() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'update_ts' field.
      * @return This builder.
      */
    public kafka.workshop.models.Product.Builder clearUpdateTs() {
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Product build() {
      try {
        Product record = new Product();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.String) defaultValue(fields()[1]);
        record.price = fieldSetFlags()[2] ? this.price : (java.lang.Integer) defaultValue(fields()[2]);
        record.create_ts = fieldSetFlags()[3] ? this.create_ts : (java.time.Instant) defaultValue(fields()[3]);
        record.update_ts = fieldSetFlags()[4] ? this.update_ts : (java.time.Instant) defaultValue(fields()[4]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Product>
    WRITER$ = (org.apache.avro.io.DatumWriter<Product>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Product>
    READER$ = (org.apache.avro.io.DatumReader<Product>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










