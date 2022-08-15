package io.dongtai.iast.core.handler;

import io.dongtai.iast.core.handler.hookpoint.vulscan.*;
import org.junit.Assert;
import org.junit.Test;

public class TaintRangeBuilderAppendTest {
    @Test
    public void testAppend() {
        TaintRanges ts;
        TaintRanges srcTs;
        TaintRanges tgtTs;
        TaintRangesBuilder tb = new TaintRangesBuilder();

        ts = new TaintRanges();
        srcTs = new TaintRanges();
        tgtTs = new TaintRanges(new TaintRange(0,3));
        // "" + "foo"
        tb.append(ts, new StringBuilder("foo"), srcTs, "foo", tgtTs, 0, 0, 0);
        Assert.assertEquals("Taints:[untrusted(0,3)]", ts.toString());

        ts = new TaintRanges();
        srcTs = new TaintRanges();
        tgtTs = new TaintRanges(new TaintRange(0,3));
        // "foo" + "bar"
        tb.append(ts, new StringBuilder("foobar"), srcTs, "bar", tgtTs, 0, 0, 0);
        Assert.assertEquals("Taints:[untrusted(3,6)]", ts.toString());

        ts = new TaintRanges();
        srcTs = new TaintRanges(new TaintRange(0,1));
        tgtTs = new TaintRanges(new TaintRange(1,2));
        // "foo" + "bar"
        tb.append(ts, new StringBuilder("foobar"), srcTs, "bar", tgtTs, 0, 0, 0);
        Assert.assertEquals("Taints:[untrusted(0,1), untrusted(4,5)]", ts.toString());
    }

    @Test
    public void testAppendStartStop() {
        TaintRanges ts;
        TaintRanges srcTs;
        TaintRanges tgtTs;
        TaintRangesBuilder tb = new TaintRangesBuilder();

        ts = new TaintRanges();
        srcTs = new TaintRanges();
        tgtTs = new TaintRanges(new TaintRange(0,5));
        // StringBuilder("foo").append("1bar2", 1, 4)
        tb.append(ts, new StringBuilder("foobar"), srcTs, "1bar2", tgtTs, 1, 4, 2);
        Assert.assertEquals("Taints:[untrusted(3,6)]", ts.toString());

        ts = new TaintRanges();
        srcTs = new TaintRanges();
        tgtTs = new TaintRanges(new TaintRange(0,3));
        // StringBuilder("foo").append("1bar2", 1, 4)
        tb.append(ts, new StringBuilder("foobar"), srcTs, "1bar2", tgtTs, 1, 4, 2);
        Assert.assertEquals("Taints:[untrusted(3,5)]", ts.toString());
    }

    @Test
    public void testAppendStartLength() {
        TaintRanges ts;
        TaintRanges srcTs;
        TaintRanges tgtTs;
        TaintRangesBuilder tb = new TaintRangesBuilder();

        ts = new TaintRanges();
        srcTs = new TaintRanges();
        tgtTs = new TaintRanges(new TaintRange(0,5));
        // StringBuilder("foo").append(char[]{"1bar2"}, 1, 3)
        tb.append(ts, new StringBuilder("foobar"), srcTs, "1bar2", tgtTs, 1, 3, 3);
        Assert.assertEquals("Taints:[untrusted(3,6)]", ts.toString());

        ts = new TaintRanges();
        srcTs = new TaintRanges();
        tgtTs = new TaintRanges(new TaintRange(0,2));
        // StringBuilder("foo").append(char[]{"1bar2"}, 1, 3)
        tb.append(ts, new StringBuilder("foobar"), srcTs, "1bar2", tgtTs, 1, 3, 3);
        Assert.assertEquals("Taints:[untrusted(3,4)]", ts.toString());

        ts = new TaintRanges();
        srcTs = new TaintRanges(new TaintRange(2,3));
        tgtTs = new TaintRanges(new TaintRange(0,2));
        // StringBuilder("foo").append(char[]{"1bar2"}, 1, 3)
        tb.append(ts, new StringBuilder("foobar"), srcTs, "1bar2", tgtTs, 1, 3, 3);
        Assert.assertEquals("Taints:[untrusted(2,4)]", ts.toString());
    }
}
