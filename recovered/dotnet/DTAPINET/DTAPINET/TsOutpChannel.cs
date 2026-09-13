using Dtapi;

namespace DTAPINET;

public sealed class TsOutpChannel : DtOutpChannel
{
	public unsafe TsOutpChannel()
		: base(Init: false)
	{
		try
		{
			Dtapi.DtOutpChannel* ptr = (Dtapi.DtOutpChannel*)global::_003CModule_003E.@new(328u);
			Dtapi.DtOutpChannel* pDtOutpChannel;
			try
			{
				pDtOutpChannel = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtOutpChannel_002E_007Bctor_007D(ptr));
			}
			catch
			{
				//try-fault
				global::_003CModule_003E.delete(ptr, 328u);
				throw;
			}
			m_pDtOutpChannel = pDtOutpChannel;
			return;
		}
		catch
		{
			//try-fault
			base.Dispose(A_0: true);
			throw;
		}
	}
}
