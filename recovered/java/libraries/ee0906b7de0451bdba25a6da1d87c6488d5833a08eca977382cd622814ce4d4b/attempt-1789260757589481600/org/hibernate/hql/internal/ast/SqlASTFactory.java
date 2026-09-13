/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  antlr.ASTFactory
 *  antlr.Token
 *  antlr.collections.AST
 */
package org.hibernate.hql.internal.ast;

import antlr.ASTFactory;
import antlr.Token;
import antlr.collections.AST;
import java.lang.reflect.Constructor;
import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
import org.hibernate.hql.internal.ast.HqlSqlWalker;
import org.hibernate.hql.internal.ast.tree.AggregateNode;
import org.hibernate.hql.internal.ast.tree.BetweenOperatorNode;
import org.hibernate.hql.internal.ast.tree.BinaryArithmeticOperatorNode;
import org.hibernate.hql.internal.ast.tree.BinaryLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
import org.hibernate.hql.internal.ast.tree.CastFunctionNode;
import org.hibernate.hql.internal.ast.tree.CollectionFunction;
import org.hibernate.hql.internal.ast.tree.ConstructorNode;
import org.hibernate.hql.internal.ast.tree.CountNode;
import org.hibernate.hql.internal.ast.tree.DeleteStatement;
import org.hibernate.hql.internal.ast.tree.DotNode;
import org.hibernate.hql.internal.ast.tree.FromClause;
import org.hibernate.hql.internal.ast.tree.FromElement;
import org.hibernate.hql.internal.ast.tree.IdentNode;
import org.hibernate.hql.internal.ast.tree.ImpliedFromElement;
import org.hibernate.hql.internal.ast.tree.InLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.IndexNode;
import org.hibernate.hql.internal.ast.tree.InitializeableNode;
import org.hibernate.hql.internal.ast.tree.InsertStatement;
import org.hibernate.hql.internal.ast.tree.IntoClause;
import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.JavaConstantNode;
import org.hibernate.hql.internal.ast.tree.LiteralNode;
import org.hibernate.hql.internal.ast.tree.MapEntryNode;
import org.hibernate.hql.internal.ast.tree.MapKeyNode;
import org.hibernate.hql.internal.ast.tree.MapValueNode;
import org.hibernate.hql.internal.ast.tree.MethodNode;
import org.hibernate.hql.internal.ast.tree.NullNode;
import org.hibernate.hql.internal.ast.tree.OrderByClause;
import org.hibernate.hql.internal.ast.tree.ParameterNode;
import org.hibernate.hql.internal.ast.tree.QueryNode;
import org.hibernate.hql.internal.ast.tree.ResultVariableRefNode;
import org.hibernate.hql.internal.ast.tree.SearchedCaseNode;
import org.hibernate.hql.internal.ast.tree.SelectClause;
import org.hibernate.hql.internal.ast.tree.SelectExpressionImpl;
import org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode;
import org.hibernate.hql.internal.ast.tree.SimpleCaseNode;
import org.hibernate.hql.internal.ast.tree.SqlFragment;
import org.hibernate.hql.internal.ast.tree.SqlNode;
import org.hibernate.hql.internal.ast.tree.UnaryArithmeticNode;
import org.hibernate.hql.internal.ast.tree.UnaryLogicOperatorNode;
import org.hibernate.hql.internal.ast.tree.UpdateStatement;

public class SqlASTFactory
extends ASTFactory
implements HqlSqlTokenTypes {
    private HqlSqlWalker walker;

    public SqlASTFactory(HqlSqlWalker walker) {
        this.walker = walker;
    }

    public Class getASTNodeType(int tokenType) {
        switch (tokenType) {
            case 45: 
            case 90: {
                return QueryNode.class;
            }
            case 50: {
                return UpdateStatement.class;
            }
            case 13: {
                return DeleteStatement.class;
            }
            case 29: {
                return InsertStatement.class;
            }
            case 30: {
                return IntoClause.class;
            }
            case 22: {
                return FromClause.class;
            }
            case 139: {
                return FromElement.class;
            }
            case 140: {
                return ImpliedFromElement.class;
            }
            case 15: {
                return DotNode.class;
            }
            case 82: {
                return IndexNode.class;
            }
            case 110: 
            case 146: {
                return IdentNode.class;
            }
            case 156: {
                return ResultVariableRefNode.class;
            }
            case 148: {
                return SqlFragment.class;
            }
            case 85: {
                return MethodNode.class;
            }
            case 77: {
                return CastFunctionNode.class;
            }
            case 17: 
            case 27: {
                return CollectionFunction.class;
            }
            case 143: {
                return SelectClause.class;
            }
            case 150: {
                return SelectExpressionImpl.class;
            }
            case 73: {
                return AggregateNode.class;
            }
            case 12: {
                return CountNode.class;
            }
            case 75: {
                return ConstructorNode.class;
            }
            case 100: 
            case 101: 
            case 102: 
            case 103: 
            case 104: 
            case 128: 
            case 131: {
                return LiteralNode.class;
            }
            case 20: 
            case 49: {
                return BooleanLiteralNode.class;
            }
            case 105: {
                return JavaConstantNode.class;
            }
            case 41: {
                return OrderByClause.class;
            }
            case 121: 
            case 122: 
            case 123: 
            case 124: 
            case 125: {
                return BinaryArithmeticOperatorNode.class;
            }
            case 95: 
            case 96: {
                return UnaryArithmeticNode.class;
            }
            case 76: {
                return SimpleCaseNode.class;
            }
            case 56: {
                return SearchedCaseNode.class;
            }
            case 130: 
            case 154: {
                return ParameterNode.class;
            }
            case 34: 
            case 88: 
            case 107: 
            case 114: 
            case 116: 
            case 117: 
            case 118: 
            case 119: {
                return BinaryLogicOperatorNode.class;
            }
            case 26: 
            case 87: {
                return InLogicOperatorNode.class;
            }
            case 10: 
            case 86: {
                return BetweenOperatorNode.class;
            }
            case 84: {
                return IsNullLogicOperatorNode.class;
            }
            case 83: {
                return IsNotNullLogicOperatorNode.class;
            }
            case 19: {
                return UnaryLogicOperatorNode.class;
            }
            case 70: {
                return MapKeyNode.class;
            }
            case 71: {
                return MapValueNode.class;
            }
            case 72: {
                return MapEntryNode.class;
            }
            case 39: {
                return NullNode.class;
            }
        }
        return SqlNode.class;
    }

    protected AST createUsingCtor(Token token, String className) {
        AST t;
        try {
            Class<?> c = Class.forName(className);
            Class[] tokenArgType = new Class[]{Token.class};
            Constructor<?> ctor = c.getConstructor(tokenArgType);
            if (ctor != null) {
                t = (AST)ctor.newInstance(token);
                this.initializeSqlNode(t);
            } else {
                t = this.create(c);
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid class or can't make instance, " + className);
        }
        return t;
    }

    private void initializeSqlNode(AST t) {
        if (t instanceof InitializeableNode) {
            InitializeableNode initializeableNode = (InitializeableNode)t;
            initializeableNode.initialize(this.walker);
        }
        if (t instanceof SessionFactoryAwareNode) {
            ((SessionFactoryAwareNode)t).setSessionFactory(this.walker.getSessionFactoryHelper().getFactory());
        }
    }

    protected AST create(Class c) {
        AST t;
        try {
            t = (AST)c.newInstance();
            this.initializeSqlNode(t);
        }
        catch (Exception e) {
            this.error("Can't create AST Node " + c.getName());
            return null;
        }
        return t;
    }
}

