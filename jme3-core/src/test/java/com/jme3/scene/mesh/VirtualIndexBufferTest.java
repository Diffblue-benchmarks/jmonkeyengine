package com.jme3.scene.mesh;

import com.jme3.scene.Mesh.Mode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class VirtualIndexBufferTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRemaining() {
        final VirtualIndexBuffer bufferPoints = new VirtualIndexBuffer(8, Mode.Points);
        assertEquals(8, bufferPoints.remaining());

        final VirtualIndexBuffer bufferLineLoop = new VirtualIndexBuffer(8, Mode.LineLoop);
        assertEquals(15, bufferLineLoop.remaining());

        final VirtualIndexBuffer bufferLineStrip = new VirtualIndexBuffer(8, Mode.LineStrip);
        assertEquals(14, bufferLineStrip.remaining());

        final VirtualIndexBuffer bufferLines = new VirtualIndexBuffer(8, Mode.Lines);
        assertEquals(8, bufferLines.remaining());

        final VirtualIndexBuffer bufferTriangleFan = new VirtualIndexBuffer(8, Mode.TriangleFan);
        assertEquals(18, bufferTriangleFan.remaining());

        final VirtualIndexBuffer bufferTriangleStrip = new VirtualIndexBuffer(8, Mode.TriangleStrip);
        assertEquals(18, bufferTriangleStrip.remaining());

        final VirtualIndexBuffer bufferTriangles = new VirtualIndexBuffer(8, Mode.Triangles);
        assertEquals(8, bufferTriangles.remaining());

        final VirtualIndexBuffer bufferPatch = new VirtualIndexBuffer(8, Mode.Patch);
        assertEquals(0, bufferPatch.remaining());
    }

    @Test
    public void testRemaining_Hybrid() {
        thrown.expect(UnsupportedOperationException.class);
        final VirtualIndexBuffer buffer = new VirtualIndexBuffer(8, Mode.Hybrid);
        buffer.remaining();
    }

    @Test
    public void testPosition() {
        final VirtualIndexBuffer buffer = new VirtualIndexBuffer(5, Mode.Points);

        assertEquals(0, buffer.position);
        buffer.get();
        assertEquals(1, buffer.position);
        buffer.rewind();
        assertEquals(0, buffer.position);
    }

    @Test
    public void testGet() {
        final VirtualIndexBuffer bufferPoints = new VirtualIndexBuffer(5, Mode.Points);
        assertEquals(5, bufferPoints.get(5));

        final VirtualIndexBuffer bufferLines = new VirtualIndexBuffer(5, Mode.Lines);
        assertEquals(5, bufferLines.get(5));

        final VirtualIndexBuffer bufferTriangles = new VirtualIndexBuffer(5, Mode.Triangles);
        assertEquals(5, bufferTriangles.get(5));

        final VirtualIndexBuffer bufferLineStrip = new VirtualIndexBuffer(5, Mode.LineStrip);
        assertEquals(3, bufferLineStrip.get(5));

        final VirtualIndexBuffer bufferLineLoop = new VirtualIndexBuffer(5, Mode.LineLoop);
        assertEquals(3, bufferLineLoop.get(5));

        final VirtualIndexBuffer bufferLineLoop2 = new VirtualIndexBuffer(5, Mode.LineLoop);
        assertEquals(0, bufferLineLoop2.get(4));

        final VirtualIndexBuffer bufferTriangleStrip = new VirtualIndexBuffer(5, Mode.TriangleStrip);
        assertEquals(2, bufferTriangleStrip.get(6));

        final VirtualIndexBuffer bufferTriangleStrip2 = new VirtualIndexBuffer(5, Mode.TriangleStrip);
        assertEquals(1, bufferTriangleStrip2.get(4));

        final VirtualIndexBuffer bufferTriangleStrip3 = new VirtualIndexBuffer(5, Mode.TriangleStrip);
        assertEquals(17, bufferTriangleStrip3.get(47));

        final VirtualIndexBuffer bufferTriangleStrip4 = new VirtualIndexBuffer(5, Mode.TriangleStrip);
        assertEquals(32, bufferTriangleStrip4.get(93));

        final VirtualIndexBuffer bufferTriangleFan = new VirtualIndexBuffer(5, Mode.TriangleFan);
        assertEquals(0, bufferTriangleFan.get(9));

        final VirtualIndexBuffer bufferTriangleFan2 = new VirtualIndexBuffer(5, Mode.TriangleFan);
        assertEquals(4, bufferTriangleFan2.get(8));
    }

    @Test
    public void testGet_Patch() {
        final VirtualIndexBuffer bufferPatch = new VirtualIndexBuffer(5, Mode.Patch);
        thrown.expect(UnsupportedOperationException.class);
        bufferPatch.get(5);
    }

    @Test
    public void testPut() {
        final VirtualIndexBuffer buffer = new VirtualIndexBuffer(5, Mode.Points);
        thrown.expect(UnsupportedOperationException.class);
        buffer.put(1, 1);
    }

    @Test
    public void testSize() {
        final VirtualIndexBuffer bufferTriangleFan = new VirtualIndexBuffer(5, Mode.TriangleFan);
        assertEquals(9, bufferTriangleFan.size());

        final VirtualIndexBuffer bufferLineLoop = new VirtualIndexBuffer(8, Mode.LineLoop);
        assertEquals(15, bufferLineLoop.size());
    }

    @Test
    public void testGetBuffer() {
        final VirtualIndexBuffer buffer1 = new VirtualIndexBuffer(5, Mode.TriangleFan);
        assertNull(buffer1.getBuffer());

        final VirtualIndexBuffer buffer2 = new VirtualIndexBuffer(12, Mode.Points);
        assertNull(buffer2.getBuffer());
    }
}
