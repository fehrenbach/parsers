package org.morphling.parsers.truffle;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.HashMap;

public abstract class GrammarPrimitive extends GrammarNode {

//    public class UninitializedNonterminalCall extends GrammarPrimitive {
//        final NonterminalName nonterminalName;
//
//        public UninitializedNonterminalCall(NonterminalName nonterminalName) {
//            this.nonterminalName = nonterminalName;
//        }
//
//        public void execute() {
//            // TODO something like this.
//            // Go to interpreter, lookup the name in some environment, replace this node by one that does not do the lookup
//            CompilerDirectives.transferToInterpreterAndInvalidate();
//
//            Alternatives rightHandSide = lookupInEnvironment(nonterminalName);
//
//            CachedNonterminalCall replacement = new CachedNonterminalCall(nonterminalName, rightHandSide);
//
//            replace(replacement);
//
//            replacement.execute();
//        }
//
//        private Alternatives lookupInEnvironment(NonterminalName nonterminalName) {
//            // TODO
//            return null;
//        }
//    }
//
//    public class CachedNonterminalCall extends UninitializedNonterminalCall {
//        final Alternatives rightHandSide;
//
//        public CachedNonterminalCall(NonterminalName nonterminalName, Alternatives rightHandSide) {
//            super(nonterminalName);
//            this.rightHandSide = rightHandSide;
//        }
//
//        // TODO execute the alternatives after checking that we did not modify the grammar (via some guard) or rewrite to uninitialized call if the grammar changed
//    }

}
