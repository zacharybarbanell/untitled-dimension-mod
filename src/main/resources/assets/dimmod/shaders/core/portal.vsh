#version 150

#moj_import <projection.glsl>

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;

out vec3 pos;
flat out vec3 flatPos;

void main() {
	vec4 camPos = inverse(ProjMat * ModelViewMat) * vec4(0.0, 0.0, -1.0, 1.0);
	camPos /= camPos.w;
	pos = IViewRotMat * (Position - camPos.xyz);
	flatPos = IViewRotMat * (Position - camPos.xyz);

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
