/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.engine.calculations;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.collect.Messages;
import com.opengamma.strata.collect.result.Result;
import com.opengamma.strata.engine.Column;

/**
 * Results of performing calculations for a set of targets over a set of scenarios.
 *
 * TODO Hand written builder - the list of Measures should be an argument to ResultsBuilder.create()
 */
@BeanDefinition
public final class Results implements ImmutableBean {

  /** Indices of columns, keyed by the measure displayed in the column. */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableMap<Column, Integer> columnIndices;

  /**
   * The results, with results for each target grouped together, ordered by column.
   * <p>
   * For example, given a set of results with two target, t1 and t2, and two columns c1 and c2, the
   * results will be:
   * <pre>
   *   [t1c1, t1c2, t2c1, t2c2]
   * </pre>
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableList<Result<?>> values;

  /** The number of rows in the results. */
  private final int rowCount;

  @ImmutableConstructor
  private Results(Map<Column, Integer> columnIndices, List<Result<?>> values) {
    this.columnIndices = ImmutableMap.copyOf(columnIndices);
    this.values = ImmutableList.copyOf(values);

    if (columnIndices.isEmpty()) {
      rowCount = 0;
    } else {
      int remainder = values.size() % columnIndices.size();

      if (remainder != 0) {
        throw new IllegalArgumentException(
            Messages.format(
                "The number of results ({}) must be exactly divisible by the number of columns ({})",
                values.size(),
                columnIndices.size()));
      }
      rowCount = values.size() / columnIndices.size();
    }
  }

  /**
   * Returns the results for a target and column for a set of scenarios.
   *
   * @param rowIndex the index of the target in the input list
   * @param column the column
   * @return the results for the specified target and column for a set of scenarios
   */
  public Result<?> get(int rowIndex, Column column) {
    if (rowIndex < 0 || rowIndex >= rowCount) {
      throw new IllegalArgumentException(
          Messages.format(
              "Row index ({}) is out of bounds. It must be between 0 and the row count ({})",
              rowIndex,
              rowCount));
    }
    Integer columnIndex = columnIndices.get(column);

    if (columnIndex == null) {
      throw new IllegalArgumentException("There is no column matching " + column);
    }
    int columnCount = columnIndices.size();
    int index = columnCount * rowIndex + columnIndex;
    return values.get(index);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code Results}.
   * @return the meta-bean, not null
   */
  public static Results.Meta meta() {
    return Results.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(Results.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static Results.Builder builder() {
    return new Results.Builder();
  }

  @Override
  public Results.Meta metaBean() {
    return Results.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets indices of columns, keyed by the measure displayed in the column.
   * @return the value of the property, not null
   */
  public ImmutableMap<Column, Integer> getColumnIndices() {
    return columnIndices;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the results, with results for each target grouped together, ordered by column.
   * <p>
   * For example, given a set of results with two target, t1 and t2, and two columns c1 and c2, the
   * results will be:
   * <pre>
   * [t1c1, t1c2, t2c1, t2c2]
   * </pre>
   * @return the value of the property, not null
   */
  public ImmutableList<Result<?>> getValues() {
    return values;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      Results other = (Results) obj;
      return JodaBeanUtils.equal(getColumnIndices(), other.getColumnIndices()) &&
          JodaBeanUtils.equal(getValues(), other.getValues());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getColumnIndices());
    hash = hash * 31 + JodaBeanUtils.hashCode(getValues());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("Results{");
    buf.append("columnIndices").append('=').append(getColumnIndices()).append(',').append(' ');
    buf.append("values").append('=').append(JodaBeanUtils.toString(getValues()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code Results}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code columnIndices} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<Column, Integer>> columnIndices = DirectMetaProperty.ofImmutable(
        this, "columnIndices", Results.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code values} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableList<Result<?>>> values = DirectMetaProperty.ofImmutable(
        this, "values", Results.class, (Class) ImmutableList.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "columnIndices",
        "values");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1548663951:  // columnIndices
          return columnIndices;
        case -823812830:  // values
          return values;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public Results.Builder builder() {
      return new Results.Builder();
    }

    @Override
    public Class<? extends Results> beanType() {
      return Results.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code columnIndices} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<Column, Integer>> columnIndices() {
      return columnIndices;
    }

    /**
     * The meta-property for the {@code values} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableList<Result<?>>> values() {
      return values;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1548663951:  // columnIndices
          return ((Results) bean).getColumnIndices();
        case -823812830:  // values
          return ((Results) bean).getValues();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code Results}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<Results> {

    private Map<Column, Integer> columnIndices = ImmutableMap.of();
    private List<Result<?>> values = ImmutableList.of();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(Results beanToCopy) {
      this.columnIndices = beanToCopy.getColumnIndices();
      this.values = beanToCopy.getValues();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1548663951:  // columnIndices
          return columnIndices;
        case -823812830:  // values
          return values;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1548663951:  // columnIndices
          this.columnIndices = (Map<Column, Integer>) newValue;
          break;
        case -823812830:  // values
          this.values = (List<Result<?>>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public Results build() {
      return new Results(
          columnIndices,
          values);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code columnIndices} property in the builder.
     * @param columnIndices  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder columnIndices(Map<Column, Integer> columnIndices) {
      JodaBeanUtils.notNull(columnIndices, "columnIndices");
      this.columnIndices = columnIndices;
      return this;
    }

    /**
     * Sets the {@code values} property in the builder.
     * @param values  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder values(List<Result<?>> values) {
      JodaBeanUtils.notNull(values, "values");
      this.values = values;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("Results.Builder{");
      buf.append("columnIndices").append('=').append(JodaBeanUtils.toString(columnIndices)).append(',').append(' ');
      buf.append("values").append('=').append(JodaBeanUtils.toString(values));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
