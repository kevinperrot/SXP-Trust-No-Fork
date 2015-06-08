package util.secure.AVProtocol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import util.secure.AsymKeysImpl;
import util.secure.ElGamal;

public class Receiver {
	
	private AsymKeysImpl keys ;
	
	public AsymKeysImpl getKeys() {
		return keys;
	}

	public void setKeys(AsymKeysImpl keys) {
		this.keys = keys;
	}
	
	/**
	 * check the proof 
	 * @param delta
	 * @param Dkeys
	 * @return
	 */
	public Boolean Verifies (Delta delta, AsymKeysImpl Dkeys)
	{
		TTP ttp = delta.getTtp();
		Proof proof = delta.getProof();
		
		for (ParticipantEx Pi : ttp.getParticipants())
		{
			BigInteger x = BigInteger.ONE;
			BigInteger produit = BigInteger.ONE;
			for (BigInteger A : proof.getListAj())
			{
				x = x.multiply(Pi.getX());
				produit = produit.multiply(A.modPow(x, Dkeys.getP()));
			}
			if (proof.getMapMi(Pi) != proof.getM().multiply(produit))
				return false;
		}
		
		return true;
	}
	
	/** 
	 * reconstruction message with interpolation Lagrange formula
	 * @param ttp
	 * @param Dkeys, dealer keys
	 * @return
	 */
	public BigInteger Reconstruction (TTP ttp, AsymKeysImpl Dkeys)
	{
		BigDecimal [] x = new BigDecimal [ttp.getK()];
		BigDecimal [] mi = new BigDecimal [ttp.getK()];
		
		for (int i = 0 ; i< ttp.getParticipants().size(); i++)
		{
			ParticipantEx Pi = ttp.getParticipant(i);
			x[i] = new BigDecimal(Pi.getX());
			mi[i] = new BigDecimal (new BigInteger (Pi.getMiD()));
		}
		
		return Lagrange.inter( x, mi, BigDecimal.ZERO).mod(Dkeys.getP());
	}
	
	/**
	 * encrypt for dealer in the end of protocol
	 * @param i
	 * @param M
	 * @param receiver
	 * @return
	 */
	public byte[] EncryptForReceiverI(int i, BigInteger M, AsymKeysImpl Dkeys )
	{
		ElGamal elGamal = new ElGamal(Dkeys);
		return elGamal.encryptWithPublicKey(M.toByteArray());
	}
	
}
