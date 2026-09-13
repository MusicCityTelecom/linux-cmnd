namespace Philips.PSG.Gateway;

public static class ConfigFactory
{
	private static Config _config;

	public static Config CurrentConfig
	{
		get
		{
			if (_config == null)
			{
				_config = Config.CreateFromFile();
			}
			return _config;
		}
	}
}
