package com.example.opengles_texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Rectangle {
	private FloatBuffer mVertexBuffer;
	private int mProgram;
	private int mPositionHandle;
	private int muMVPMatrixHandle;
	private int mColorHandle;
	private int muMMatrixHandle;
	private int muLightLocationHandle;
	private int mTextureCoordHandle;
	private int textureId;
	private int muTextureHandle;

	private Context mContext;
	public Rectangle(Context context) {
		this.mContext = context;
		initVetexData(2);
	}

	public void initVetexData(int i) {
		float vertices[] = new float[] {
				// 顶点             颜色            纹理坐标
	        	//前面
				0, 0, 1,  1,1,1,0,  0.5f*i, 0.5f*i,
				1, 1, 1,  1,0,0,0,  1.0f*i, 0.0f*i,
				-1, 1, 1,  1,0,0,0,  0.0f*i, 0.0f*i,
				0, 0, 1,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1, 1, 1,  1,0,0,0,  0.0f*i, 0.0f*i,
				-1,-1, 1,  1,0,0,0,  0.0f*i, 1.0f*i,
				0, 0, 1,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1,-1, 1,  1,0,0,0,  0.0f*i, 1.0f*i,
				1,-1, 1,  1,0,0,0,  1.0f*i, 1.0f*i,
				0, 0, 1,  1,1,1,0,  0.5f*i, 0.5f*i,
				1,-1, 1,  1,0,0,0,  1.0f*i, 1.0f*i,
				1, 1, 1,  1,0,0,0,  1.0f*i, 0.0f*i,
				//后面
				0, 0,-1,  1,1,1,0,  0.5f*i, 0.5f*i,
				1, 1,-1,  1,0,0,0,  1.0f*i, 0.0f*i,
				1,-1,-1,  1,0,0,0,   0.0f*i, 0.0f*i,
				0, 0,-1,  1,1,1,0,  0.5f*i, 0.5f*i,
				1,-1,-1,  1,0,0,0,   0.0f*i, 0.0f*i,
				-1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				0, 0,-1,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				-1, 1,-1,  1,0,0,0, 1.0f*i, 1.0f*i,
				0, 0,-1,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1, 1,-1,  1,0,0,0, 1.0f*i, 1.0f*i,
				1, 1,-1,  1,0,0,0,  1.0f*i, 0.0f*i,
				//左面
				-1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				-1, 1, 1,  1,0,0,0, 1.0f*i, 0.0f*i,
				-1, 1,-1,  1,0,0,0,  0.0f*i, 0.0f*i,
				-1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				-1, 1,-1,  1,0,0,0,  0.0f*i, 0.0f*i,
				-1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				-1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				-1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				-1,-1, 1,  1,0,0,0, 1.0f*i, 1.0f*i,
				-1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				-1,-1, 1,  1,0,0,0, 1.0f*i, 1.0f*i,
				-1, 1, 1,  1,0,0,0, 1.0f*i, 0.0f*i,
				//右面
				1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				1, 1, 1,  1,0,0,0, 1.0f*i, 0.0f*i*i,
				1,-1, 1,  1,0,0,0,  0.0f*i, 0.0f*i,
				1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				1,-1, 1,  1,0,0,0,  0.0f*i, 0.0f*i,
				1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				1, 1,-1,  1,0,0,0, 1.0f*i, 1.0f*i,
				1, 0, 0,  1,1,1,0, 0.5f*i, 0.5f*i,
				1, 1,-1,  1,0,0,0, 1.0f*i, 1.0f*i,
				1, 1, 1,  1,0,0,0, 1.0f*i, 0.0f*i,
				//上面
				0, 1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				1, 1, 1,  1,0,0,0,  1.0f*i, 0.0f*i,
				1, 1,-1,  1,0,0,0,   0.0f*i, 0.0f*i,
				0, 1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				1, 1,-1,  1,0,0,0,   0.0f*i, 0.0f*i,
				-1, 1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				0, 1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1, 1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				-1, 1, 1,  1,0,0,0, 1.0f*i, 1.0f*i,
				0, 1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1, 1, 1,  1,0,0,0, 1.0f*i, 1.0f*i,
				1, 1, 1,  1,0,0,0,  1.0f*i, 0.0f*i,
				//下面
				0,-1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				1,-1, 1,  1,0,0,0,  1.0f*i, 0.0f*i,
				-1,-1, 1,  1,0,0,0,  0.0f*i, 0.0f*i,
				0,-1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1,-1, 1,  1,0,0,0,  0.0f*i, 0.0f*i,
				-1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				0,-1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				-1,-1,-1,  1,0,0,0,  0.0f*i, 1.0f*i,
				1,-1,-1,  1,0,0,0,  1.0f*i, 1.0f*i,
				0,-1, 0,  1,1,1,0,  0.5f*i, 0.5f*i,
				1,-1,-1,  1,0,0,0,  1.0f*i, 1.0f*i,
				1,-1, 1,  1,0,0,0,  1.0f*i, 0.0f*i
	        };
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);

		int vertexShader = loaderShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = loaderShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

		mProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(mProgram, vertexShader);
		GLES20.glAttachShader(mProgram, fragmentShader);
		GLES20.glLinkProgram(mProgram);

		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
		mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
		
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
		muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");
		muLightLocationHandle = GLES20.glGetUniformLocation(mProgram, "uLightLocation");
		muTextureHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");
		initTexture();
	}

	// 初始化纹理
	public void initTexture() {
		int [] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		textureId = textures[0];
		// 激活纹理单元,默认激活的就是0号纹理单元
		//GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		// 将纹理对象ID绑定到当前活动的纹理单元0上的GL_TEXTURE_2D目标
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		//缩小采样使用最近点采样
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST_MIPMAP_NEAREST);
		//缩小采样使用最近点采样
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		//纹理包裹拉伸方式在st轴采用截取拉伸方式，这些设置指的是对坐标范围超过1的部分的限制
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_MIRRORED_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_MIRRORED_REPEAT);
		
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.texture);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}
	
	public void draw() {
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 12*6);
	}

	public void setValue(float[] mvpMatrix, float[] mMatrix) {
		GLES20.glUseProgram(mProgram);
		mVertexBuffer.position(0);
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, (4+3+2) * 4, mVertexBuffer);
		mVertexBuffer.position(3);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, (4+3+2) * 4, mVertexBuffer);
        mVertexBuffer.position(7);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT, false, (4+3+2) * 4, mVertexBuffer);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glEnableVertexAttribArray(mColorHandle);
		GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
		
		GLES20.glUniform3f(muLightLocationHandle, 0, 0, 20);
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0);
		GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, mMatrix, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glUniform1i(muTextureHandle, 0);
	}

	private int loaderShader(int type, String shaderCode) {
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		return shader;
	}
	
	private String vertexShaderCode = "uniform mat4 uMVPMatrix;"
			+ "attribute vec2 aTextureCoord;"
			+ "varying vec2 vTextureCoord;"
			+ "uniform mat4 uMMatrix;"
			+ "uniform vec3 uLightLocation;"
			+ "attribute vec4 aColor;"
			+ "varying  vec4 vColor;"  
			+ "varying vec4 vDiffuse;"
			+ "attribute vec3 aPosition;"
			+ "void main(){"  
			+ "vec3 normalVectorOrigin = aPosition;"
			+ "vec3 normalVector = normalize((uMMatrix*vec4(normalVectorOrigin,1)).xyz);"
			+ "vec3 vectorLight = normalize(uLightLocation - (uMMatrix * vec4(aPosition,1)).xyz);"
			+ "float factor = max(0.0, dot(normalVector, vectorLight));"
			+ "vDiffuse = factor*vec4(1,1,1,1.0);"
			+ "gl_Position = uMVPMatrix * vec4(aPosition,1);"
			+ "vColor = aColor;"
			+ "vTextureCoord = aTextureCoord;"
			+ "}";

	private String fragmentShaderCode = "precision mediump float;"
			+ "uniform sampler2D uTexture;"
			+ "varying vec2 vTextureCoord;"
			+ "varying  vec4 vColor;"
			+ "varying vec4 vDiffuse;"
			+ "void main(){"
			+ "gl_FragColor = (vColor*vDiffuse + vColor*vec4(0.6,0.6,0.6,1))*texture2D(uTexture, vTextureCoord);"
			+ "}";
	
}
