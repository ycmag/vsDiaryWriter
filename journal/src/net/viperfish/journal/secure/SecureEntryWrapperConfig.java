package net.viperfish.journal.secure;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.viperfish.journal.secureAlgs.AlgorithmSpec;
import net.viperfish.utils.config.ComponentConfig;

public class SecureEntryWrapperConfig extends ComponentConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5227765591860888711L;

	private Set<String> getEncryptionOptions() {
		Set<String> result = new TreeSet<String>();
		for (String i : AlgorithmSpec.getSupportedBlockCipher()) {
			result.add("algorithm:" + i);
		}
		for (String i : AlgorithmSpec.getSupportedBlockCipherMode()) {
			result.add("mode:" + i);
		}
		for (String i : AlgorithmSpec.getSupportedBlockCipherPadding()) {
			result.add("padding:" + i);
		}
		return result;
	}

	private Set<String> getMacOptions() {
		return getAvailableMac();
	}

	private Set<String> getMacAlgOptions() {
		String macMethod = getProperty("MacMethod");
		if (macMethod.equalsIgnoreCase("CMAC")
				|| macMethod.equalsIgnoreCase("CBCMAC")
				|| macMethod.equalsIgnoreCase("CFBMAC")) {
			return AlgorithmSpec.getSupportedBlockCipher();
		} else if (macMethod.equalsIgnoreCase("GMAC")) {
			return AlgorithmSpec.getGmacAlgorithms();
		} else if (macMethod.equalsIgnoreCase("HMAC")) {
			return AlgorithmSpec.getSupportedDigest();
		}
		return new TreeSet<>();
	}

	public SecureEntryWrapperConfig() {
		super("secureEntryWrapper");
	}

	private Set<String> getAvailableMac() {
		Set<String> result = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		result.add("CMAC");
		result.add("GMAC");
		result.add("CBCMAC");
		result.add("CFBMAC");
		result.add("HMAC");
		return result;
	}

	@Override
	public List<String> requiredConfig() {
		return new LinkedList<>();
	}

	@Override
	public List<String> optionalConfig() {
		List<String> result = new LinkedList<>();
		result.add("EncryptionMethod");
		result.add("MacMethod");
		result.add("MacAlgorithm");
		return result;
	}

	@Override
	public void fillInDefault() {
		this.setProperty("MacMethod", "HMAC");
		this.setProperty("EncryptionMethod", "AES/CFB/PKCS7PADDING");
		this.setProperty("MacAlgorithm", "SHA512");
	}

	@Override
	public Set<String> getOptions(String key) {
		if (key.equals("EncryptionMethod")) {
			return getEncryptionOptions();
		} else if (key.equals("MacMethod")) {
			return getMacOptions();
		} else if (key.equals("MacAlgorithm")) {
			return getMacAlgOptions();
		} else {
			return new TreeSet<>();
		}
	}

}