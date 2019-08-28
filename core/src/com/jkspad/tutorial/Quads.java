package com.jkspad.tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author John Knight
 * Copyright http://www.jkspad.com
 *
 */
public class Quads extends ApplicationAdapter {
	private Mesh quadMesh;
	private ShaderProgram shader;

	private final String VERTEX_SHADER =
			"attribute vec4 a_position;\n"
					+ "attribute vec4 a_color;\n"
					+ "varying vec4 v_color;\n"
					+ "void main() {\n"
					+ " gl_Position = a_position;\n"
					+ " v_color = a_color;\n" +
					"}";

	private final String FRAGMENT_SHADER =
			"varying vec4 v_color;\n"
					+ "void main() {\n"
					+ " gl_FragColor = v_color;\n"
					+ "}";


	protected void createMeshShader() {
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		String log = shader.getLog();
		if (!shader.isCompiled()){
			throw new GdxRuntimeException(log);
		}
		if (log!=null && log.length()!=0){
			Gdx.app.log("shader log", log);
		}
	}


	private void createQuadMesh(){
		if (quadMesh == null) {
			quadMesh = new Mesh(true, 4, 0,
					new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"),
					new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "a_color"));


			// left red to bottom white
//			quadMesh.setVertices(new float[] {
//					-0.5f, -0.5f, 0, Color.toFloatBits(255, 0, 0, 255), 		// red bottom left
//					0.5f, -0.5f, 0, Color.toFloatBits(255, 255, 255, 255), 		// white bottom right
//					-0.5f, 0.5f, 0,  Color.toFloatBits(255, 0, 0, 255),			// red top left
//					0.5f, 0.5f, 0,  Color.toFloatBits(255, 255, 255, 255) });	// white top right

			// Top red to bottom white
			quadMesh.setVertices(new float[] {
					-0.5f, -0.5f, 0, Color.toFloatBits(255, 255, 255, 255), 	// white bottom left
					0.5f, -0.5f, 0, Color.toFloatBits(255, 255, 255, 255), 	// white bottom right
					-0.5f, 0.5f, 0,  Color.toFloatBits(255, 0, 0, 255),		// red red top left
					0.5f, 0.5f, 0,  Color.toFloatBits(255, 0, 0, 255) });		// red top right
		}
	}

	@Override
	public void create() {
		createQuadMesh();
		createMeshShader();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shader.begin();
		quadMesh.render(shader, GL20.GL_TRIANGLE_STRIP, 0, 4);
		shader.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		shader.dispose();
		quadMesh.dispose();
	}
}
