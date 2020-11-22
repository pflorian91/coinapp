package coinapp.domain.model;

/**
 * Transaction
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
public class Transaction {

  private TransactionStatus status;

  private String id;

  private Transaction(TransactionStatus status, String id) {
    this.status = status;
    this.id = id;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public String getId() {
    return id;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Transaction that = (Transaction) o;

    if (status != that.status) {
      return false;
    }
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    int result = status.hashCode();
    result = 31 * result + id.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "status=" + status +
        ", id='" + id + '\'' +
        '}';
  }

  public static class TransactionBuilder {

    private TransactionStatus status;

    private String id;

    public static TransactionBuilder aTransaction() {
      return new Transaction.TransactionBuilder();
    }

    public Transaction build() {
      return new Transaction(status, id);
    }

    public TransactionBuilder withStatus(TransactionStatus status) {
      this.status = status;
      return this;
    }

    public TransactionBuilder withId(String id) {
      this.id = id;
      return this;
    }
  }
}
