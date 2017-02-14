package com.cap.poc.model;

import java.io.Serializable;

public class Product implements Serializable {

  private String productId;

  private String name;

  private int quantity;

  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public int getQuantity() {

    return this.quantity;
  }

  public void setQuantity(int quantity) {

    this.quantity = quantity;
  }

  public String getProductId() {

    return this.productId;
  }

  public void setProductId(String productId) {

    this.productId = productId;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.productId == null) ? 0 : this.productId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Product other = (Product) obj;
    if (this.productId == null) {
      if (other.productId != null)
        return false;
    } else if (!this.productId.equals(other.productId))
      return false;
    return true;
  }

  @Override
  public String toString() {

    return "Product [productId=" + this.productId + ", name=" + this.name + ", quantity=" + this.quantity + "]";
  }

}
