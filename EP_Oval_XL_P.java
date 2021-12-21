public class EP_Oval_XL_P extends EP_Oval {
    EP_ShadowObject_XL_P center;
    public EP_Oval_XL_P(Vector initPos, EP_ShadowObject_XL_P center, Path_Orbit path){
        super(initPos, new Vector(), "white");
        this.center = center;
        destroyOutsideBounds = false;
        newPath(path);
    }

    @Override
    public void update(){
        if(!center.getExists()){
            followsPath = false;
            destroyOutsideBounds = true;
        }
        super.update();
    }
}
