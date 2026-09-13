using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtCmPath
{
	public enum Type
	{
		CONSTANT_DELAY,
		CONSTANT_DOPPLER,
		RAYLEIGH_JAKES,
		RAYLEIGH_GAUSSIAN
	}

	public Type m_Type;

	public double m_Attenuation;

	public double m_Delay;

	public double m_Phase;

	public double m_Doppler;

	[return: MarshalAs(UnmanagedType.U1)]
	public unsafe bool operator ==(DtCmPath Rhs)
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPath dtCmPath);
		*(int*)(&dtCmPath) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 8)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 16)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 24)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 32)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPath dtCmPath2);
		*(int*)(&dtCmPath2) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath2, 8)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath2, 16)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath2, 24)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath2, 32)) = 0.0;
		ConvertToUnmgd(&dtCmPath);
		Rhs.ConvertToUnmgd(&dtCmPath2);
		return global::_003CModule_003E.Dtapi_002EDtCmPath_002E_003D_003D(&dtCmPath, &dtCmPath2);
	}

	[return: MarshalAs(UnmanagedType.U1)]
	public bool operator !=(DtCmPath Rhs)
	{
		return !op_Equality(Rhs);
	}

	internal unsafe void ConvertToUnmgd(Dtapi.DtCmPath* uCmPath)
	{
		*(Type*)uCmPath = m_Type;
		((double*)uCmPath)[1] = m_Attenuation;
		((double*)uCmPath)[2] = m_Delay;
		((double*)uCmPath)[3] = m_Phase;
		((double*)uCmPath)[4] = m_Doppler;
	}

	internal unsafe void ConvertFromUnmgd(Dtapi.DtCmPath* uCmPath)
	{
		m_Type = *(Type*)uCmPath;
		m_Attenuation = ((double*)uCmPath)[1];
		m_Delay = ((double*)uCmPath)[2];
		m_Phase = ((double*)uCmPath)[3];
		m_Doppler = ((double*)uCmPath)[4];
	}

	public unsafe DtCmPath()
	{
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtCmPath dtCmPath);
		*(int*)(&dtCmPath) = 0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 8)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 16)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 24)) = 0.0;
		System.Runtime.CompilerServices.Unsafe.As<Dtapi.DtCmPath, double>(ref System.Runtime.CompilerServices.Unsafe.AddByteOffset(ref dtCmPath, 32)) = 0.0;
		ConvertFromUnmgd(&dtCmPath);
	}
}
