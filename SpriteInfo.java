public class SpriteInfo {
    private String name;
    private Vector offset;
    private double rotation;
    private double size;

    //linked list
    private SpriteInfo next;

    public SpriteInfo(){
        name = "unnamed sprite";
        offset = new Vector();
        rotation = 0;
        size = 1;
    }

    public SpriteInfo(String name){
        this.name = name;
        offset = new Vector();
        rotation = 0;
        size = 1;
    }

    public SpriteInfo(String name, Vector offset){
        this.name = name;
        this.offset = offset;
        rotation = 0;
        size = 1;
    }

    public SpriteInfo(String name, Vector offset, double rotation, double size){
        this.name = name;
        this.offset = offset;
        this.rotation = rotation;
        this.size = size;
    }

    public SpriteInfo(String name, Vector offset, SpriteInfo next){
        this.name = name;
        this.offset = offset;
        rotation = 0;
        size = 1;
        this.next = next;
    }

    public SpriteInfo(String name, Vector offset, double rotation, double size, SpriteInfo next){
        this.name = name;
        this.offset = offset;
        this.rotation = rotation;
        this.size = size;
        this.next = next;
    }

    public String getName() {
        return name;
    }

    public Vector getOffset() {
        return offset;
    }

    public double getRotation() {
        return rotation;
    }

    public double getSize() {
        return size;
    }

    public SpriteInfo getNext() {
        if(next != null)
            return next;
        else
            return this;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;

        //between 0 and 360
        if(rotation >= 360)
            rotation -= 360;
        else if(rotation < 0)
            rotation += 360;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setNext(SpriteInfo next){
        this.next = next;
    }
}

