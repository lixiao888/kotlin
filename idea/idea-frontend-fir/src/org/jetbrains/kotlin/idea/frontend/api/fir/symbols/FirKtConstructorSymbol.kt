/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.frontend.api.fir.symbols

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.declarations.FirConstructor
import org.jetbrains.kotlin.idea.fir.findPsi
import org.jetbrains.kotlin.idea.frontend.api.Invalidatable
import org.jetbrains.kotlin.idea.frontend.api.TypeInfo
import org.jetbrains.kotlin.idea.frontend.api.fir.utils.asTypeInfo
import org.jetbrains.kotlin.idea.frontend.api.fir.utils.cached
import org.jetbrains.kotlin.idea.frontend.api.fir.utils.weakRef
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtConstructorParameterSymbol
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtConstructorSymbol
import org.jetbrains.kotlin.idea.frontend.api.symbols.KtSymbolKind
import org.jetbrains.kotlin.idea.frontend.api.withValidityAssertion

internal class FirKtConstructorSymbol(
    fir: FirConstructor,
    override val token: Invalidatable
) : KtConstructorSymbol(),
    FirKtSymbol<FirConstructor> {
    override val fir: FirConstructor by weakRef(fir)
    override val psi: PsiElement? by cached { fir.findPsi(fir.session) }

    override val type: TypeInfo by cached { fir.returnTypeRef.asTypeInfo(fir.session, token) }
    override val valueParameters: List<KtConstructorParameterSymbol> by cached<List<KtConstructorParameterSymbol>> { TODO() }

    override val isPrimary: Boolean get() = withValidityAssertion { fir.isPrimary }
    override val symbolKind: KtSymbolKind get() = KtSymbolKind.MEMBER
}