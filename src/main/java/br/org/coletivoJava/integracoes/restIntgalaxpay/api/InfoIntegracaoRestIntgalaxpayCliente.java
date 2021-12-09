package br.org.coletivoJava.integracoes.restIntgalaxpay.api;

import javax.inject.Qualifier;
import br.org.coletivoJava.integracoes.intGalaxPay.api.FabApiRestIntGalaxPayCliente;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InfoIntegracaoRestIntgalaxpayCliente {

	FabApiRestIntGalaxPayCliente tipo();
}