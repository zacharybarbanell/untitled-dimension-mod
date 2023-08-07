#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int EndPortalLayers;

uniform int px;
uniform int pz;
uniform int nx;
uniform int nz;

in vec3 pos;
flat in vec3 flatPos;

out vec4 fragColor;

void main() {
	vec3 dir = normalize(pos);
	float dx = mod(pos.x - flatPos.x, 1.0);
	float dz = mod(pos.z - flatPos.z, 1.0);
	float tx;
	if(dir.x < 0) {
		tx = (dx + px) / -dir.x;
	}
	else {
		tx = (nx + 1.0 - dx) / dir.x;
	}
	float tz;
	if(dir.z < 0) {
		tz = (dz + pz) / -dir.z;
	}
	else {
		tz = (nz + 1.0 - dz) / dir.z;
	}
	float t = min(tx, tz);
	float h = dir.y * t;
    fragColor = vec4(sin(h*10) * exp(h/20.0), 0.0, 0.0, 1.0);
}
