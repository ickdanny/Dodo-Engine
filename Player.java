public abstract class Player extends Entity {

    private static Player thePlayer;
    protected boolean canBomb = true;
    protected boolean focused = false;
    protected boolean lastFocused = focused;
    protected int bombs = 0;
    protected int power = 0;
    protected int invulnerableTimer = 0;

    //3 seconds invul after being hit
    protected final int INVULNERABLE_DURATION = 3* Driver_Game.getUPS();
    protected final int BOMB_INVULNERABLE_DURATION = 8 * Driver_Game.getUPS();
    //THESE ARE DIFFERENT FOR EVERY PLAYER ENTITY
    protected double baseSpeed = 0, baseFocusSpeed = 0;

    //fields for graphics
    protected SpriteInfo left, leftTurn3, leftTurn2, leftTurn1, idle, rightTurn1, rightTurn2, rightTurn3, right;
    //-4 to 4
    protected int spritePos = 0;

    //delay for players is ALWAYS BOTH ATTACK AND RELOAD DELAY (!!!)
    public Player(double size, Hitbox hitbox, double delay, int hp, int bombs, int power){
        //size = 10; <- individual classes pls
        //position is same always
        super(new Vector(Driver_Game.getWidth() / 2, Driver_Game.getHeight() /*/ 2*/- 100), size);
        canDamage = true;
        canBeDamaged = true;
        this.hp = hp;
        this.bombs = bombs;
        this.power = power;
        if(this.power > 200){
            this.power = 200;
        }
        if(this.power <= 0){
            this.power = 1;
        }
        damage = 0;
        setTrueHitbox(hitbox);
        attackDelay = delay;
        reloadDelay = delay;
        attackName = "default";
    }

    @Override
    public void update(){
        super.update();
        if(invulnerableTimer > 0){
            invulnerableTimer--;
        }
    }

    @Override
    protected void move(){
        previousPosition.setToThis(this);
        if(!lastFocused) {
            addToThis(Vector.scalarMultiple(velocity, baseSpeed * ((double)1 / Driver_Game.getUPS())));
        }
        else{
            addToThis(Vector.scalarMultiple(velocity, baseFocusSpeed * ((double)1/ Driver_Game.getUPS())));
        }
        //lastFocused = focused;
        twoFrameHitbox = AABB.twoFrameAABB(previousPosition);
        baseMovementBehavior();
    }

    //makes certain the player is unable to leave the game field
    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        Vector nextPos;
        if(!lastFocused){
            nextPos = Vector.add(this, Vector.scalarMultiple(velocity, baseSpeed * ((double)1 / Driver_Game.getUPS())));
        }
        else{
            nextPos = Vector.add(this, Vector.scalarMultiple(velocity, baseFocusSpeed * ((double)1 / Driver_Game.getUPS())));
        }

        if(nextPos.a < 15){
            if(!lastFocused){
                //find the velocity needed to reach 0 precisely, and divide by base speed/UPS
                double zeroDiff = 0 - this.getA() + 15;
                velocity.a = zeroDiff/(baseSpeed * ((double)1)/ Driver_Game.getUPS());
            }
            else{
                double zeroDiff = 0 - this.getA() + 15;
                velocity.a = zeroDiff/(baseFocusSpeed * ((double)1)/ Driver_Game.getUPS());
            }
        }
        else if(nextPos.a > Driver_Game.getWidth() - 15){
            if(!lastFocused) {
                double maxDiff = Driver_Game.getWidth() - this.getA() - 15;
                velocity.a = maxDiff / (baseSpeed * ((double) 1) / Driver_Game.getUPS());
            }
            else{
                double maxDiff = Driver_Game.getWidth() - this.getA() - 15;
                velocity.a = maxDiff / (baseFocusSpeed * ((double) 1) / Driver_Game.getUPS());
            }
        }

        if(nextPos.b < 30){
            if(!lastFocused){
                double zeroDiff = 0 - this.getB() + 30;
                velocity.b = zeroDiff/(baseSpeed * ((double)1)/ Driver_Game.getUPS());
            }
            else{
                double zeroDiff = 0 - this.getB() + 30;
                velocity.b = zeroDiff/(baseFocusSpeed * ((double)1)/ Driver_Game.getUPS());
            }
        }
        else if(nextPos.b > Driver_Game.getHeight() - 30){
            if(!lastFocused) {
                double maxDiff = Driver_Game.getHeight() - this.getB() - 30;
                velocity.b = maxDiff / (baseSpeed * ((double) 1) / Driver_Game.getUPS());
            }
            else{
                double maxDiff = Driver_Game.getHeight() - this.getB() - 30;
                velocity.b = maxDiff / (baseFocusSpeed * ((double) 1) / Driver_Game.getUPS());
            }
        }

        //at the very end set lastfocused
        lastFocused = focused;
    }

    //the player cannot bomb when there are bomb projectiles in existence. This is done in Driver_GameLogic.
    //player is invulnerable for 8 seconds
    public void bomb(){
        if(bombs > 0 && this.canBomb) {
            this.spawnBomb();
            bombs--;
            this.canBomb = false;
            invulnerableTimer = BOMB_INVULNERABLE_DURATION;
        }
    }

    //override this
    protected void spawnBomb(){

    }

    public void focus(boolean b){
        focused = b;
    }

    public void setCanBomb(boolean canBomb){
        this.canBomb = canBomb;
    }
    public int getBombs(){
        return bombs;
    }
    public static void createPlayer(int hp, int bombs, int power, String playerCode){
        switch(playerCode){
            //make players and bind to thePlayer
            case "AbaddonA":
                thePlayer = new Player_AbaddonA(hp, bombs, power);
                break;
            case "AbaddonB":
                thePlayer = new Player_AbaddonB(hp, bombs, power);
                break;
            case "AzraelA":
                thePlayer = new Player_AzraelA(hp, bombs, power);
                break;
            case "AzraelB":
                thePlayer = new Player_AzraelB(hp, bombs, power);
                break;
            case "MiraA":
                thePlayer = new Player_MiraA(hp, bombs, power);
                break;
            case "MiraB":
                thePlayer = new Player_MiraB(hp, bombs, power);
                break;
            default:
                break;
        }
    }
    public static Player getThePlayer(){
        return thePlayer;
    }

    public int getPower() {
        return power;
    }

    @Override
    public void collides(Entity e){
        //some shit
        if(canBeDamaged && (e instanceof Enemy || e instanceof Projectile_Enemy) && e.getCanDamage()){
            if(invulnerableTimer == 0) {
                updateHP(e.getDamage());
                Driver_GameLogic.getCurrentLogicDriver().playerHit();
                invulnerableTimer = INVULNERABLE_DURATION;
            }
        }
        if(e instanceof Pickup_PowerUp){
            power += ((Pickup_PowerUp)e).getPower();
            if(power > 200){
                power = 200;
            }
        }
        else if(e instanceof Pickup_Bomb){
            bombs++;
        }
        else if(e instanceof Pickup_Life){
            hp++;
        }
    }

    @Override
    protected void updateHP(double damage){
        if(!canBeDamaged || !exists)
            return;
        hp -= damage;
        if(hp < 0){
            exists = false;
            deathRoutine();
            Driver_GameLogic.getCurrentLogicDriver().defeat();
        }
        else{
            Driver_GameLogic.getCurrentLogicDriver().projectileClear();
            hitPowerChange();
        }
    }

    private void hitPowerChange(){
        int halfOfPower = power/2;
        int thirtyOff = power - 30;
        int newPower = Math.max(halfOfPower, thirtyOff);
//        int newPower;
//        if(power <= 30)
        int diff = power - newPower;
        power = newPower;
        int spawnPower = diff/2;
        if(spawnPower < 0){
            spawnPower = 0;
        }
        if(spawnPower != 0) {
            spawnPower++;
        }
        int num10s = spawnPower/10;
        for(int i = 0; i < num10s; i++){
            spawnPower(false);
        }
        spawnPower -= (num10s * 10);
        for(int i = 0; i < spawnPower; i++){
            spawnPower(true);
        }
    }

    private void spawnPower(boolean small){
        double angle = 90 + (Math.random() * 180);
        double dist = 80 + (Math.random() * 80);
        Vector p = new Vector(angle, dist);
        Vector v = Vector.polarToVelocity(p);
        Vector spawnPos = Vector.add(this, v);
        if(small){
            toSpawn.add(new Pickup_PowerUpSmall(spawnPos));
        }
        else{
            toSpawn.add(new Pickup_PowerUpLarge(spawnPos));
        }
    }

    @Override
    public Vector getVelocityVector(){
        if(!focused) {
            return Vector.scalarMultiple(velocity, baseSpeed);
        }
        else{
            return Vector.scalarMultiple(velocity, baseFocusSpeed);
        }
    }

    @Override
    public Vector getPolarVector(){
        if(!focused){
            return new Vector(polar.a, polar.b * baseSpeed);
        }
        else{
            return new Vector(polar.a, polar.b * baseFocusSpeed);
        }
    }

    public boolean getFocused(){
        return this.focused;
    }
    //probably override spawnAttack in individual classes

    public void addBomb(){
        bombs++;
    }

    @Override
    protected void updateSprite() {
        if (tick % 5 == 0) {
            Vector nextPos;
            if (!lastFocused) {
                nextPos = Vector.add(this, Vector.scalarMultiple(Vector.polarToVelocity(polar), baseSpeed * ((double) 1 / Driver_Game.getUPS())));
            } else {
                nextPos = Vector.add(this, Vector.scalarMultiple(Vector.polarToVelocity(polar), baseFocusSpeed * ((double) 1 / Driver_Game.getUPS())));
            }

            int nextSpritePos;
            //left
            if (nextPos.getA() < this.getA() - .05) {
                //either it is not the correct sprite
                if (spritePos > -4)
                    nextSpritePos = spritePos - 1;
                //or it is
                else
                    nextSpritePos = -4;
            }
            //right
            else if (nextPos.getA() > this.getA() + .05) {
                //either it is not the correct sprite
                if (spritePos < 4)
                    nextSpritePos = spritePos + 1;
                //or it is
                else
                    nextSpritePos = 4;
            }
            //idle
            else {
                //either it is to the left of idle
                if (spritePos < 0)
                    nextSpritePos = spritePos + 1;
                //to the right of idle
                else if (spritePos > 0)
                    nextSpritePos = spritePos - 1;
                //or already idle
                else
                    nextSpritePos = 0;
            }

            //only change the sprite if it needs to be changed
            if (nextSpritePos != spritePos) {
                spritePos = nextSpritePos;
                if(spritePos == -4)
                    this.sprite = left;
                else if(spritePos == -3)
                    this.sprite = leftTurn3;
                else if(spritePos == -2)
                    this.sprite = leftTurn2;
                else if(spritePos == -1)
                    this.sprite = leftTurn1;
                else if(spritePos == 0)
                    this.sprite = idle;
                else if(spritePos == 1)
                    this.sprite = rightTurn1;
                else if(spritePos == 2)
                    this.sprite = rightTurn2;
                else if(spritePos == 3)
                    this.sprite = rightTurn3;
                else if(spritePos == 4)
                    this.sprite = right;
            }
            else
                this.sprite = this.sprite.getNext();
        }
    }

    public void victoryInvulnerable(){
        invulnerableTimer = 100000;
    }
}