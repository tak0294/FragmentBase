package fam.tak0294.sample.cache;


public class Cache {
	public static enum TYPE { COIN, PAPER };
	
	private int m_amount;
	private Cache.TYPE m_type;

	//•\Ž¦—p.
	private int m_resId;
	
	public Cache(int amount, int resId)
	{
		m_resId = resId;
		m_amount = amount;
		//1000‰~ˆÈã‚Í‚¨ŽD.
		if(amount >= 1000)
			m_type = Cache.TYPE.PAPER;
		else
			m_type = Cache.TYPE.COIN;
	}

	public int getAmount() {
		return m_amount;
	}

	public Cache.TYPE getType() {
		return m_type;
	}
	
	public int getResId()
	{
		return m_resId;
	}
}
