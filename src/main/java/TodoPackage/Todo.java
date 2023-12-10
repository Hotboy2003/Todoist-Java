package TodoPackage;
public class Todo
{
    private String id;
    private String title;
    private boolean completed;
    private Long created_at;
    private Long updated_at;
    private Long completed_at;

    // конструктор
    public Todo(String title)
    {
        this.id = generateId();
        this.title = title;
        this.completed = false;
        this.created_at = System.currentTimeMillis();
        this.updated_at = null;
        this.completed_at = null;
    }

    // генерация зашифрованного id
    private String generateId()
    {
        String uid = Long.toHexString(Double.doubleToLongBits(Math.random()));
        String timestamp = Long.toHexString(System.currentTimeMillis());
        return uid + timestamp;
    }

    public void setId(String value) {
        this.id = value;
    }

    public void setCompleted(boolean value) {
        this.completed = value;
    }

    public void setCreated_at(long value) {
        this.created_at = value;
    }

    public void setUpdated_at(Long value) {
        this.updated_at = value;
    }

    public void setCompleted_at(Long value) {
        this.completed_at = value;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public long getCreated_at() {
        return this.created_at;
    }

    public Long getUpdated_at() {
        return this.updated_at;
    }

    public Long getCompleted_at() {
        return this.completed_at;
    }
}
