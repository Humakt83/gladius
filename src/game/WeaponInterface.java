package game;

public abstract interface WeaponInterface {
	public abstract String getWeaponType();
	public abstract int getMinDam();
    public abstract int getMaxDam();
	public abstract int battleDamage();
	public abstract String getName();
    public abstract int getPrice();
}
